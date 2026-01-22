package com.start.ecom.repositores;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.ecom.models.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    public Optional<Cart> findByUserId(int user_id);

}
