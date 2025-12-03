package com.start.ecom.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItemDto {

    private int cartId;
    private int productId;
    private int quantity;
    private List<String> selectedOption;
    
}
