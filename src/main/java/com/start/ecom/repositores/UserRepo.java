package com.start.ecom.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.ecom.models.Users;
@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    
}
