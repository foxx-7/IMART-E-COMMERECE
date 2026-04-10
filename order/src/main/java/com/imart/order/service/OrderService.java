package com.imart.order.service;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.CartItem;
import com.imart.order.dto.local.CheckOut;
import com.imart.order.exception.InvalidAddressException;
import com.imart.order.exception.ResourceNotFoundException;
import com.imart.order.kafka.producer.*;
import com.imart.order.dto.local.TransactionRecord;
import com.imart.order.exception.InvalidSessionException;
import com.imart.order.exception.UnprocessableRequestException;
import com.imart.order.model.Order;
import com.imart.order.model.OrderStatus;
import com.imart.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaProducer kafkaProducer;
    private final OrderRepository orderRepository;
    private final SessionService sessionService;
    private final AddressValidationService addressValidationService;

    @Qualifier("redisVaultTemplate")
    private final RedisTemplate<String , Object> vaultRedisTemplate;

    public Order confirmOrder(TransactionRecord transactionRecord){

        final String redisKey = "checkout:" + transactionRecord.getUserId();
        CheckOut checkOut = (CheckOut) vaultRedisTemplate.opsForValue().get(redisKey);

        if(checkOut == null){
            throw new UnprocessableRequestException(" null exception encountered while processing checkout");
        }
        if(!sessionService.validateCheckOutSession(transactionRecord.getUserId())){
            throw new InvalidSessionException("invalid session accessed");
        }

        Order order = Order.builder()
                .userId(transactionRecord.getUserId())
                .items(checkOut.getItems())
                .checkOutAmount(transactionRecord.getAmount())
                .shippingAddress(checkOut.getShippingAddress())
                .status(OrderStatus.ORDER_CONFIRMED)
                .isActive(true)
                .build();

        orderRepository.save(order);

        sessionService.endCheckOutSession(transactionRecord.getUserId());

       return order;
    }

    //get active user orders
    public List<Order> fetchActiveOrder(Long userId){
       return orderRepository.findAllByUserIdAndIsActiveTrue(userId);
    }

    //get order history
    public List<Order> fetchAll(Long userId){
        return orderRepository.findAllByUserId(userId);
    }

    //cancel active order
   public void cancelOrder(Long userId, String checkOutId){
            Optional<Order> orderOpt = orderRepository.
                    findByUserIdAndCheckOutIdAndIsActiveTrue(userId, UUID.fromString(checkOutId));
            if(orderOpt.isEmpty()) {
                throw new ResourceNotFoundException("order not found");
            }
            Order order = orderOpt.get();
           orderRepository.delete(order);
   }

   //update order shipping address
   public void updateShippingAddress(Long userId, Address addressUpdate){
       if(addressValidationService.validateAddress(addressUpdate)){
          throw new InvalidAddressException("invalid address provided");
       }
       Optional<Order> orderOpt = orderRepository.findByUserId(userId);
       if(orderOpt.isEmpty()){
           throw new ResourceNotFoundException("order not found");
       }
       Order order = orderOpt.get();

       order.setShippingAddress(addressUpdate);

       orderRepository.save(order);
   }

   //remove item from order cart
   public void  removeItemFromOrder(Long userId,Long productId){

       Optional<Order> orderOpt = orderRepository.findByUserId(userId);
       if(orderOpt.isEmpty()){
           throw new ResourceNotFoundException("order not found");
       }

       Order order = orderOpt.get();
       List<CartItem> cartItems = order.getItems();

       cartItems.removeIf(item -> item.getProductId().equals(productId));

        order.setItems(cartItems);
       orderRepository.save(order);

   }

   //mark order as inactive
   public void deactivateOrder(Long userId, String checkOutId){
        Optional<Order> orderOpt = orderRepository.findByUserIdAndCheckOutIdAndIsActiveTrue(userId, UUID.fromString(checkOutId));
        if(orderOpt.isEmpty()){
           throw new ResourceNotFoundException("order not found");
        }
        Order order = orderOpt.get();
        order.setActive(false);

        orderRepository.save(order);
   }
}
