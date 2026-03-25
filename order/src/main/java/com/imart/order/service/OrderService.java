package com.imart.order.service;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.Cart;
import com.imart.order.dto.foreign.CartState;
import com.imart.order.dto.foreign.InventoryCheckResponse;
import com.imart.order.dto.local.*;
import com.imart.order.feignclients.InventoryServiceFeignClient;
import com.imart.order.feignclients.UserServiceFeignClient;
import com.imart.order.model.Order;
import com.imart.order.model.OrderStatus;
import com.imart.order.publisher.KafkaPublisher;
import com.imart.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceFeignClient inventoryServiceFeignClient;
    private final UserServiceFeignClient userServiceFeignClient;
    private final KafkaPublisher kafkaPublisher;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${app.kafka.topics.producer.order-initiated-topic}")
    private  String orderInitiatedTopic;

    @Value("${app.kafka.topics.producer.order-created-topic}")
    private  String orderCreatedTopic;

    @Value("${app.kafka.topics.producer.awaiting-shipping-address-topic}")
    private String awaitingShippingAddressTopic;

    @Value("${app.kafka.topics.producer.ready-for-payment-topic}")
    private String readyForPaymentTopic;

    @Value("${app.kafka.topics.publisher.awaiting-shipping-address-selection=topic")
    private String awaitingShippingAddressSelectionTopic;

    public void initiateOrderFromCheckOut(CheckOutEvent checkOutEvent) {
        Long userId = checkOutEvent.getCart().getUserId();
        String checkOutSessionId = checkOutEvent.getSessionId();


        InventoryCheckResponse inventoryCheckResponse = inventoryServiceFeignClient
                .processStockAvailability(checkOutEvent.getCart().getItems());

        List<Address> availableShippingAddresses = userServiceFeignClient.getAvailableShippingAddresses(userId);

        //if inventory response  declares affected stock quantity
        if (!inventoryCheckResponse.getOutOfStockItemsList().isEmpty()) {

            //adjust cart based on inventory response data
            CartState cartState = CartState.builder()
                    .userId(checkOutEvent.getCart().getUserId())
                    .id(checkOutEvent.getCart().getId())
                    .availableItemList(inventoryCheckResponse.getAvailableItemsList())
                    .outOfStockItems(inventoryCheckResponse.getOutOfStockItemsList())
                    .subTotal(getTotal(inventoryCheckResponse))
                    .build();

            checkOutEvent.setCart(cartState);

            if(availableShippingAddresses.isEmpty()){
                handleNoShippingAddress(checkOutEvent);
            }
            else {
                handleAddressChoice(checkOutEvent, availableShippingAddresses);
            }
        }

        // cart inventory intact**
        if(availableShippingAddresses.isEmpty()){
            handleNoShippingAddress(checkOutEvent);
        }
        else {
            handleAddressChoice(checkOutEvent, availableShippingAddresses);
        }
    }

    public void handleNoShippingAddress(CheckOutEvent checkOutEvent){
        final String checkOutSessionId = checkOutEvent.getSessionId();
        PendingAddressRequest pendingAddressRequest = PendingAddressRequest.builder()
                .checkOutSessionId(checkOutSessionId)
                .cart((Cart) checkOutEvent.getCart())
                .availableAddresses(null)
                .requiresNewAddress(true)
                .requiresAddressChoice(false)
                .status(OrderStatus.AWAITING_ADDRESS)
                .build();

        String rediskey = "checkout:pending:" + checkOutSessionId;

        //checkout data stored in redis with 30 minutes lifespan
        redisTemplate.opsForValue().set(rediskey, pendingAddressRequest, 30, TimeUnit.MINUTES);

        //publish event for frontend to pickup and provide a valid shipping address
        kafkaPublisher.publishEvent(awaitingShippingAddressTopic, pendingAddressRequest);
    }

    public void handleAddressChoice(CheckOutEvent checkOutEvent, List<Address> addresses){
        final String checkOutSessionId = checkOutEvent.getSessionId();
        PendingAddressRequest pendingAddressRequest = PendingAddressRequest.builder()
                .checkOutSessionId(checkOutSessionId)
                .cart((Cart) checkOutEvent.getCart())
                .availableAddresses(addresses)
                .status(OrderStatus.AWAITING_ADDRESS_SELECTION)
                .requiresAddressChoice(PendingAddressRequest.setAddressRequirement(addresses))
                .requiresNewAddress(!PendingAddressRequest.setAddressRequirement(addresses))
                .build();

        String rediskey = "checkout:pending:" + checkOutSessionId;

        redisTemplate.opsForValue().set(rediskey,pendingAddressRequest, 30, TimeUnit.MINUTES);


        kafkaPublisher.publishEvent(awaitingShippingAddressSelectionTopic, pendingAddressRequest);
    }

    public OrderResponse createOrder(Long userId, OrderRequest request){
        Order order = Order.builder()
                .cart(request.getCart())
                .checkOutAmount(request.getTotalAmount())
                .status(OrderStatus.ORDER_CREATED)
                .build();
        orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .userId(userId)
                .items(request.getCart().getItems())
                .totalAmount(request.getTotalAmount())
                .status(OrderStatus.ORDER_CREATED)
                .creationTimeStamp(LocalDateTime.now())
                .build();

        kafkaPublisher.publishEvent(orderCreatedTopic, orderCreatedEvent);
    }

;    public void processForPayment(ReadyforPaymentRequest request){

    }

    private BigDecimal getTotal(InventoryCheckResponse inventoryCheckResponse){
        BigDecimal total = BigDecimal.ZERO;
        for(InventoryCheckResponse.AvailableItem item: inventoryCheckResponse.getAvailableItemsList()){
            total = total.add((item.getPrice().multiply(BigDecimal.valueOf(item.getReservedQuantity()))));
        }

        if(!inventoryCheckResponse.getOutOfStockItemsList().isEmpty()){
            for(InventoryCheckResponse.OutOfStockItem item:inventoryCheckResponse.getOutOfStockItemsList()){
                total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getAvailableQuantity())));
            }
        }
        return total;
    }
}
