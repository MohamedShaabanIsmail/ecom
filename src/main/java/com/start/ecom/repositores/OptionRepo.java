package com.start.ecom.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.ecom.models.Option;

@Repository
public interface OptionRepo extends JpaRepository<Option, Integer> {
    
}
