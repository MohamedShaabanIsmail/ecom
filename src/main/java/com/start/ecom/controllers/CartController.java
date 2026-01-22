package com.start.ecom.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.ecom.DTOs.CartItemDto;
import com.start.ecom.models.Cart;
import com.start.ecom.models.CartItem;
import com.start.ecom.services.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/getCart/{user_id}")
    public ResponseEntity<?> getCart(@PathVariable int user_id) {
        Cart cart = cartService.getCartByUserId(user_id);
        if(cart == null) 
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody CartItemDto item) {
        try{
            cartService.addItemToCart(item);
            return ResponseEntity.ok("Item added to cart");
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // @PutMapping("/updateCart/{id}")
    // public ResponseEntity<?> updateCart(@PathVariable int id, @RequestBody Cart cart) {
    //     try {
    //         cart = cartService.updateCart(id, cart);
    //         if(cart == null) 
    //             return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
            
    //         return ResponseEntity.ok(cart);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    //     }
    // }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<?> updateCartItem(@PathVariable int id, @RequestBody CartItem item) {

        try {
            item = cartService.updateCartItem(id, item);
            if(item == null) 
                return new ResponseEntity<>("Cart Item not found", HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeItem/{id}")
    public ResponseEntity<?> removeItem(@PathVariable int id) {
        try {
            boolean removed = cartService.removeItemFromCart(id);
            if (!removed) {
                return new ResponseEntity<>("Cart Item not found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok("Item removed");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}
