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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin
@RequestMapping("/api")
@Tag(name = "Product Management", description = "API endpoints for managing e-commerce products")
public class ProductController {

    @Autowired
    private ProductService services;

    @GetMapping("/products")
    @Operation(summary = "Get all products", description = "Retrieve a paginated list of all products with optional filtering")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductPage> getAllProducts(
            @Parameter(description = "Page number (1-indexed)", example = "1") @RequestParam(required = false, defaultValue = "1") int pageNum,
            @Parameter(description = "Number of products per page", example = "5") @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @Parameter(description = "Filter criteria for products") @RequestBody(required = false) FilterRequest filterRequest) {

        Sort direction = order.equalsIgnoreCase("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        return ResponseEntity.ok().body(services.getAllProducts(PageRequest.of(pageNum - 1, pageSize, direction), filterRequest));
    }

    @PostMapping("/product")
    @Operation(summary = "Create a new product", description = "Add a new product with image upload")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to create product")
    })
    public ResponseEntity<?> addProduct(
            @Parameter(description = "Product details") @RequestPart Product product,
            @Parameter(description = "Product image file") @RequestPart MultipartFile imageFile) {

        try {
            Product productCreated = services.product(product, imageFile);
            return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductsById(
            @Parameter(description = "Product ID", example = "1") @PathVariable int id) {
        Product product = services.getProductsById(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/{id}/image")
    @Operation(summary = "Get product image", description = "Retrieve the image for a specific product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Image retrieved successfully", content = @Content(mediaType = "image/*")),
            @ApiResponse(responseCode = "404", description = "Product or image not found")
    })
    public ResponseEntity<byte[]> getImage(
            @Parameter(description = "Product ID", example = "1") @PathVariable int id) { 
        Product prod = services.getProductsById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(prod.getImageType())).body(prod.getImageData());
    }

    @GetMapping("/products/search")
    @Operation(summary = "Search products", description = "Search for products by keyword (searches in name, description, and brand)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPage.class))),
            @ApiResponse(responseCode = "404", description = "No products found matching the keyword")
    })
    public ResponseEntity<ProductPage> search(
            @Parameter(description = "Search keyword", example = "Nike") @RequestParam String keyword,
            @Parameter(description = "Page number (1-indexed)", example = "1") @RequestParam(required = false, defaultValue = "1") int pageNum,
            @Parameter(description = "Number of products per page", example = "5") @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @Parameter(description = "Filter criteria for products") @RequestBody(required = false) FilterRequest filterRequest) 
            {
        
        Sort direction = order.equalsIgnoreCase("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        ProductPage products = services.getProductsbyKey(keyword, PageRequest.of(pageNum - 1, pageSize, direction), filterRequest);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "Delete product by ID", description = "Delete a specific product by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", example = "1") @PathVariable int id) {
        boolean deleted = services.deleteProduct(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/product/{id}")
    @Operation(summary = "Update product by ID", description = "Update the details of a specific product by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Failed to update product")
    })
    public ResponseEntity<?> updateProduct(
            @Parameter(description = "Product ID", example = "1") @PathVariable int id,
            @Parameter(description = "Updated product details") @RequestPart Product updatedProduct,
            @Parameter(description = "Updated product image") @RequestPart MultipartFile imageFile) {
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