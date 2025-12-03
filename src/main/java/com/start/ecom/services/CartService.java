package com.start.ecom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.start.ecom.DTOs.CartItemDto;
import com.start.ecom.models.Cart;
import com.start.ecom.models.CartItem;
import com.start.ecom.repositores.CartItemRepo;
import com.start.ecom.repositores.CartRepo;
import com.start.ecom.repositores.ProductRepo;
import com.start.ecom.repositores.UserRepo;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;
    
    @Autowired
    private CartItemRepo itemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    public Cart getCartByUserId(int user_id) {
        return cartRepo.findByUserId(user_id).orElse(null);
    }
    
    public void addItemToCart(CartItemDto item) throws Exception {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(productRepo.findById(item.getProductId()).orElseThrow(() -> new Exception("Product not found")));
        cartItem.setCart(cartRepo.findById(item.getCartId()).orElseThrow(() -> new Exception("Cart not found")));
        cartItem.setQuantity(item.getQuantity());
        cartItem.setSelectedOption(item.getSelectedOption());
        itemRepo.save(cartItem);
    }

    // public Cart updateCart(int id, Cart cart) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'updateCart'");
    // }

    public CartItem updateCartItem(int id, CartItem item) {
        return itemRepo.findById(id).map(saved -> {
            saved.setQuantity(item.getQuantity());
            saved.setSelectedOption(item.getSelectedOption());
            return itemRepo.save(saved);
        }).orElse(null);
    }

    public boolean removeItemFromCart(int id) {
        if(itemRepo.existsById(id))
            itemRepo.deleteById(id);
        else
            return false;
        return true;
    }

    public void create(Cart cart) throws Exception {
        userRepo.findById(cart.getUser().getId()).orElseThrow(() -> new Exception("User not found"));
        cartRepo.save(cart);
    }

    public CartItem getItem(int id) {
        return itemRepo.findById(id).orElse(null);
    }
    
}
