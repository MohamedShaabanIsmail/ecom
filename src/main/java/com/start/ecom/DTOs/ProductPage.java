package com.start.ecom.DTOs;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.start.ecom.models.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductPage {

    private List<Product> products;
    private int totalNumberOfPages;
    private int pageNumber;
    private int pageSize;
    private boolean isLastPage;
    private boolean isEmpty;
    private Sort sort;

}
