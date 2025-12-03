package com.start.ecom.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.start.ecom.models.OptionValue;

@Repository
public interface OptionValueRepo extends JpaRepository<OptionValue, Integer> {
    
}
