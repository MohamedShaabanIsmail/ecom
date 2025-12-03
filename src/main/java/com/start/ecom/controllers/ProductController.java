package com.start.ecom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.start.ecom.DTOs.FilterRequest;
import com.start.ecom.DTOs.ProductPage;
import com.start.ecom.models.Product;
import com.start.ecom.services.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService services;

    @PostMapping("/products")
    public ResponseEntity<ProductPage> getAllProducts( @RequestParam(required = false, defaultValue = "1") int pageNum, 
    @RequestParam(required = false, defaultValue = "8") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sort,
             @RequestParam(required = false, defaultValue = "desc") String order, 
            @RequestBody(required = false) FilterRequest filterRequest) {

        Sort direction = order.equalsIgnoreCase("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        return ResponseEntity.ok().body(services.getAllProducts(PageRequest.of(pageNum , pageSize, direction), filterRequest));
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile
        ) {

        try {
            Product productCreated = services.product(product, imageFile);
            return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductsById(@PathVariable int id) {
        Product product = services.getProductsById(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) { 
        Product prod = services.getProductsById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(prod.getImageType())).body(prod.getImageData());
    }

    @PostMapping("/products/search")
    public ResponseEntity<ProductPage> search(@RequestParam String keyword, @RequestParam(required = false, defaultValue = "1") int pageNum,
            @RequestParam(required = false, defaultValue = "8") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestBody(required = false) FilterRequest filterRequest) 
            {
        
        Sort direction = order.equalsIgnoreCase("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        ProductPage products = services.getProductsbyKey(keyword, PageRequest.of(pageNum , pageSize, direction), filterRequest);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct( @PathVariable int id) {
        boolean deleted = services.deleteProduct(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct( @PathVariable int id, @RequestPart Product updatedProduct,
            @RequestPart MultipartFile imageFile) {
        try {
            Product product = services.updateProduct(id, updatedProduct, imageFile);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}