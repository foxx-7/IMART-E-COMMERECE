package com.imart.cart.service;

import com.imart.cart.dto.foreign.Product;
import com.imart.cart.dto.foreign.ProductInventory;
import com.imart.cart.dto.local.CartDto;
import com.imart.cart.dto.local.CartItemRequest;
import com.imart.cart.dto.local.CartItemResponse;
import com.imart.cart.dto.local.CheckOutEvent;
import com.imart.cart.exception.NotFoundException;
import com.imart.cart.exception.ResourceUnavaliableException;
import com.imart.cart.exception.UncompleteableActionException;
import com.imart.cart.feignclient.InventoryServiceFeignClient;
import com.imart.cart.feignclient.ProductServiceFeignClient;
import com.imart.cart.model.Cart;
import com.imart.cart.model.CartItem;
import com.imart.cart.publisher.KafkaPublisher;
import com.imart.cart.repository.CartItemRepository;
import com.imart.cart.repository.CartRepository;
import com.imart.cart.utility.CartItemResponseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final  CartItemRepository cartItemRepository;
    private final KafkaPublisher kafkaPublisher;
    private final ProductServiceFeignClient productServiceFeignClient;
    private final InventoryServiceFeignClient inventoryServiceFeignClient;
    private final CartRepository cartRepository;
    @Value("${app.kafka.topics.producer.checkout-event}")
    private String checkOutEventTopic;

    //** create or update existing user cart
    @Transactional
    public CartItem addToCart(Long userId, CartItemRequest request){
        Optional<Product> productOpt = Optional.ofNullable(productServiceFeignClient.getProductById(request.getProductId()));
        if (productOpt.isEmpty()){
            throw new ResourceUnavaliableException("product resource not available");
        }
        Product product =  productOpt.get();
        ProductInventory productInventory = inventoryServiceFeignClient.getProductInventory(product.getId());
        if (productInventory.getAvailableStockQuantity() < request.getQuantity()){
            throw new UncompleteableActionException("not enough stock for demanded product quantity");
        }
        Optional<Cart> existingCartOpt = cartRepository.findByUserId(userId);
        //check if user already has a cart
       if (existingCartOpt.isPresent()){
           Cart existingCart = existingCartOpt.get();
          Optional<CartItem> existingCartItemOpt = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
          //update price and quantity if user already has the item in cart
          if(existingCartItemOpt.isPresent()) {
              CartItem existingCartItem = existingCartItemOpt.get();
              existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
              existingCartItem.setCumulativePrice(existingCartItem.getCumulativePrice()
                      .add(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));
              cartItemRepository.save(existingCartItem);
              return existingCartItem;
          }
          //else we just create a new cartitem for the product and add it to the user cart
          else{
              CartItem cartItem = CartItem.builder()
                      .userId(userId)
                      .productId(request.getProductId())
                      .quantity(request.getQuantity())
                      .cumulativePrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                      .cart(existingCart)
                      .build();
              existingCart.addItem(cartItem);
              cartRepository.save(existingCart);
              return cartItem;
           }
          //if user doesnt hava a cart, we create a new cart for the user,create new cartitem for the  product and save them for future updates or checkout
       }else{
           //create new cart item
           CartItem cartItem = CartItem.builder()
                   .userId(userId)
                   .cumulativePrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                   .build();
           Cart cart = new Cart();
           cart.setUserId(userId);
           cart.addItem(cartItem);
           cartRepository.save(cart);
           return cartItem;
       }
    }

    // delete cart item  from cart using userId and productid
    @Transactional
    public void removeFromCart(Long userId, Long productId){
        if(!cartItemRepository.existsByUserIdAndProductId(userId,  productId)){
            throw new NotFoundException("cart item does not exist");
        }
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    //get all cart items for user with id: userId
    @Transactional
    public List<CartItem> getUserCart(Long userId){
        if(!cartItemRepository.existsByUserId(userId)){
            throw new NotFoundException("cart  for user: " + userId + " does not exist");
        }
        List<CartItemResponse> cartItems = cartItemRepository.findByUserId(userId)
                .stream()
                .map(CartItemResponseMapper::mapToCartItemResponse)
                .toList();
        if(cartItems.isEmpty()){
            //***second validation might be unnecessary since user existence is already checked in repository before proceeding fetch user cart items***
            throw new UncompleteableActionException("user cart is empty");
        }
        return cartItemRepository.findByUserId(userId);
    }

    //only admins are able to fetch all cart items in repository
    @Transactional
    public List<CartItem> fetchAllCartItems() {
        return cartItemRepository.findAll();
    }

    //checkout user cart event producer
    @Transactional
    public @Nullable Void checkOut(Long userId){
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        if(items.isEmpty()){
            throw new NotFoundException("cart is empty");
        }
        CartDto cartDto = CartDto.builder()
                .items(items.stream().map(CartItemResponseMapper::mapToCartItemResponse).toList())
                .subtotal(processTotalAmount(items))
                .cartId(items.get(0).getCart().getId())
                .userId(userId).build();
        CheckOutEvent event = new CheckOutEvent(cartDto);
        kafkaPublisher.publishEvent(checkOutEventTopic, event);
        return null;
    }
 //clear user cart after checkout .
    @Transactional
    public void clearCart(Long userId){
        //related cartitems will be deleted from db due to orphan removal relationship
        cartRepository.deleteByUserId(userId);
    }

//calculate total price of items in cart...subtotal
    private BigDecimal processTotalAmount(List<CartItem> items){
        BigDecimal total = BigDecimal.ZERO;
        if(items == null || items.isEmpty()){
            throw new UncompleteableActionException("cart is null or empty");
        }
        for (CartItem item  : items){
            total = total.add(item.getCumulativePrice());
        }
        return total;
    }
}

