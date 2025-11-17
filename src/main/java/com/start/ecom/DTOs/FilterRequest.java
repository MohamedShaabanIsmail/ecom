package com.start.ecom.DTOs;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FilterRequest {
    
    private String brand;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private String category;

}
