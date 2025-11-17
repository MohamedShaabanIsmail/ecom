package com.start.ecom.DTOs;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.start.ecom.models.Product;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductPage {

    List<Product> products;
    int totalNumberOfPages;
    int pageNumber;
    int pageSize;
    boolean isLastPage;
    boolean isEmpty;
    Sort sort;

}
