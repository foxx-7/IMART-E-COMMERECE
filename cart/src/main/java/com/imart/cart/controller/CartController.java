package com.imart.cart.controller;

import com.imart.cart.dto.local.CartItemRequest;
import com.imart.cart.dto.local.CartItemResponse;
import com.imart.cart.model.CartItem;
import com.imart.cart.service.CartService;
import com.imart.cart.utility.CartItemResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.imart.cart.utility.CartItemResponseMapper.mapToCartItemResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CartController {
    private final CartService cartService;

    //**add items to user cart
    @PostMapping("/carts")
    public ResponseEntity<CartItemResponse> addItemToCart(
        @RequestHeader("user-id")  Long  userId,
        @RequestBody  CartItemRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(mapToCartItemResponse(cartService.addToCart(userId,request)));
    }

    @PostMapping("/carts/checkout")
   public ResponseEntity<Void> checkOutCart(@RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.checkOut(userId));
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartItem>> getUserCart(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getUserCart(userId));
    }

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartItem>> getAllCArtItems(){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.fetchAllCartItems());
    }


    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<String> removeItemFromCart(@RequestHeader("X-User-Id") Long userId,
                                                     @PathVariable Long productId){
    cartService.removeFromCart(userId,productId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("item removed successfully");
    }
}
