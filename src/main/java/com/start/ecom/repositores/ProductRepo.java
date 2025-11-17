package com.start.ecom.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.start.ecom.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :key,'%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :key,'%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :key, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :key, '%'))")
    List<Product> getByKey(String key);
}
