package com.start.ecom.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.ecom.models.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {

    void deleteAllByCartId(int cartid);
    
}
