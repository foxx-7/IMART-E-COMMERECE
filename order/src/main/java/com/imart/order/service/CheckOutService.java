package com.imart.order.service;

import com.imart.order.dto.foreign.*;
import com.imart.order.dto.local.*;
import com.imart.order.exception.*;
import com.imart.order.feignclient.InventoryServiceFeignClient;
import com.imart.order.feignclient.UserServiceFeignClient;
import com.imart.order.kafka.producer.KafkaProducer;
import com.imart.order.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckOutService {

    private final OrderService orderService;
    private final SessionService sessionService;
    private final AddressValidationService addressValidationService;
    private final InventoryServiceFeignClient inventoryServiceFeignClient;
    private final UserServiceFeignClient userServiceFeignClient;
    private final KafkaProducer kafkaProducer;

    @Value("${app.service.redirection.url.address-resolution-service}")
    private final String addressResolutionServiceUrl;
    @Value("${app.service.redirection.url.payment-processing-service}")
    private final String paymentProcessingServiceUrl;
    @Value("${app.service.redirection.url.cart-validation-service}")
    private final String cartValidationServiceUrl;

    @Qualifier("turboRedisTemplate")
    private final RedisTemplate<String, Object> turboRedisTemplate;

    @Qualifier("vaultRedisTemplate")
    private final RedisTemplate<String ,Object> vaultRedisTemplate;

    @Value("${app.kafka.topics.producer.payment-request-topic}")
    private final String paymentRequestTopic;

    private final String CHECKOUT_KEY = "checkout:";

    public Object initializeCheckOut(Long userId, List<CartItem> items) {

        //check if user has active outstanding checkout and return it
        if (!userServiceFeignClient.isLoggedIn(userId)) {
            throw new UnprocessableRequestException("user is not logged in");
        }

        if(sessionService.hasActiveSession(userId)){
            CheckOut checkOut = (CheckOut) vaultRedisTemplate.opsForValue().get(CHECKOUT_KEY+userId);
            if(!checkOut.getStatus().equals(OrderStatus.AWAITING_PAYMENT_CONFIRMATION)){

               PendingCheckOut pendingCheckOut = new PendingCheckOut();
               pendingCheckOut.setCheckOut(checkOut);

               switch (checkOut.getStatus()){
                   case AWAITING_ADDRESS -> pendingCheckOut.setResumptionUrl(addressResolutionServiceUrl);
                   case AWAITING_PAYMENT -> pendingCheckOut.setResumptionUrl(paymentProcessingServiceUrl);
                   case PENDING -> pendingCheckOut.setResumptionUrl(cartValidationServiceUrl);
                   default -> {}
               }
            }
        }

        CheckOut checkOut= new CheckOut();
        checkOut.setUserId(userId);
        checkOut.setCheckOutSessionId(sessionService.createCheckOutSession(userId).getSessionId());
        checkOut.setStatus(OrderStatus.PENDING);
        checkOut.setCreationTimestamp(LocalDateTime.now());

        final String redisKey = "checkout:" + userId;

        vaultRedisTemplate.opsForValue().set(redisKey, checkOut);

        return inventoryServiceFeignClient.processStockAvailability(items);
    }

    //validate and update checkout cart data
    public InventoryCheckResponse validateCart(Long userId, InventoryCheckResponse inventoryCheckResponse) {

        for(InventoryCheckResponse.OutOfStockItem item : inventoryCheckResponse.getOutOfStockItemsList()){
            if(item.getAvailableQuantity() == 0 || item.getAvailableQuantity() < item.getRequestedQuantity()){
                throw  new InvalidCartStateException("item out of stock for requested quantity");
            }
        }

        final String redisKey = "checkout:" + userId;

        CheckOut checkOut  =(CheckOut) vaultRedisTemplate.opsForValue().get(redisKey);
        if(checkOut == null){
            throw new UnprocessableRequestException("required data is null");
        }
       if(!sessionService.validateCheckOutSession(userId)){
           throw new InvalidSessionException("invalid session accessed");
       }

       List<CartItem> items = generateValidCart(userId, inventoryCheckResponse);
        checkOut.setItems(items);
        checkOut.setTotal(calculateAmount(items));
        checkOut.setStatus(OrderStatus.AWAITING_ADDRESS);
        vaultRedisTemplate.opsForValue().set(redisKey, checkOut);
        return inventoryCheckResponse;
    }

    //fetch user addresses/address
    public List<Address> provideShippingAddress( Long userId) {
        final String redisKey= "checkout:" + userId;
        CheckOut checkOut = (CheckOut) vaultRedisTemplate.opsForValue().get(redisKey);
        if(checkOut == null){
            throw new UnprocessableRequestException("request cannot be processed");
        }
        if (!sessionService.validateCheckOutSession(userId)) {
            throw new InvalidSessionException("invalid checkout session");
        }
        return userServiceFeignClient.getAvailableShippingAddresses(userId);
    }

    //update checkout shipping address data
    public Address resolveShippingAddress(Long userId ,Address address) {
        if (!addressValidationService.validateAddress(address)) {
            throw new InvalidAddressException("invalid address provided");
        }

        final String redisKey = "checkout:" + userId;

        CheckOut checkOut = (CheckOut) vaultRedisTemplate.opsForValue().get(redisKey);
        if (checkOut == null) {
            throw new UnprocessableRequestException("request cannot be processed");
        }
        if (sessionService.validateCheckOutSession(userId)){
            throw new InvalidSessionException("invalid session accessed");
        }

        checkOut.setShippingAddress(address);
        checkOut.setStatus(OrderStatus.AWAITING_PAYMENT);
        vaultRedisTemplate.opsForValue().set(redisKey, checkOut);
        return address;
    }

    //initialize payment action
    public void initializePayment(Long userId){

        final String redisKey = "checkout:" + userId;
         CheckOut checkOut = (CheckOut) vaultRedisTemplate.opsForValue().get(redisKey);

         if(checkOut == null){
             throw new UnprocessableRequestException("invalid request");
         }

         if(!sessionService.validateCheckOutSession(userId)){
             throw new InvalidSessionException("invalid session accessed");
         }
        PaymentRequest paymentRequest = new PaymentRequest
                (checkOut.getCheckOutSessionId(), userId, checkOut.getTotal(), LocalDateTime.now());

        vaultRedisTemplate.opsForValue().set(redisKey, checkOut);
        kafkaProducer.publishEvent(paymentRequestTopic, paymentRequest);
        checkOut.setStatus(OrderStatus.AWAITING_PAYMENT_CONFIRMATION);
    }

    private BigDecimal calculateAmount(List<CartItem> items){
        BigDecimal total = BigDecimal.ZERO;
        for(CartItem item : items){
            total = total.add(item.getCumulativePrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

    private List<CartItem> generateValidCart(Long userId, InventoryCheckResponse inventoryCheckResponse){
        List<CartItem>cartItems = new ArrayList<>();

       for(InventoryCheckResponse.AvailableItem availableItem : inventoryCheckResponse.getAvailableItemsList()) {
           CartItem item = CartItem.builder()
                   .userId(userId)
                   .productId(availableItem.getProductId())
                   .quantity(availableItem.getRequestedQuantity())
                   .cumulativePrice(availableItem.getPrice().multiply(BigDecimal.valueOf(availableItem.getRequestedQuantity())))
                   .build();
           cartItems.add(item);
       }

       for(InventoryCheckResponse.OutOfStockItem outOfStockItem : inventoryCheckResponse.getOutOfStockItemsList()){
           CartItem item = CartItem.builder()
                   .userId(userId)
                   .productId(outOfStockItem.getProductId())
                   .quantity(outOfStockItem.getRequestedQuantity())
                   .cumulativePrice(outOfStockItem.getPrice().multiply(BigDecimal.valueOf(outOfStockItem.getRequestedQuantity())))
                   .build();
           cartItems.add(item);
       }
       return cartItems;
    }

    public void endSession(Long userid, UUID checkOutSessionId){
        sessionService.endCheckOutSession(userid);
    }
}