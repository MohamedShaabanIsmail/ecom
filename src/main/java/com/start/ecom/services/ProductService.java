package com.start.ecom.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.start.ecom.DTOs.FilterRequest;
import com.start.ecom.DTOs.ProductPage;
import com.start.ecom.models.Product;
import com.start.ecom.repositores.ProductRepo;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    // public Page<Product> getAllProducts(int pageNum, int pageSize) {
    //     return repo.findAll(PageRequest.of(pageNum-1, pageSize));
    // }

    public ProductPage getAllProducts(PageRequest pageRequest,FilterRequest filterRequest) {

        Page<Product> page;
        if(filterRequest == null)
            page = repo.findAll(pageRequest);
        page = repo.findAll(FilterBySpecification.filter(filterRequest), pageRequest);

        ProductPage productPage = new ProductPage(page.getContent(),
                page.getTotalPages(),
                page.getNumber()+1,
                page.getSize(),
                page.isLast(),
                page.isEmpty(),
                pageRequest.getSort()
                );
        return productPage;
    }

    @Cacheable(value = "my_products", key = "#id")
    public Product getProductsById(int id) {
        System.out.println("Fetching from DB for id: " + id);
        return repo.findById(id).orElse(null);
    }

    @CachePut(value = "my_products", key = "#product.id")
    public Product product(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        System.out.println("Added to cache for id: " + product.getId());
        return repo.save(product);
    }

    public List<Product> getProductsbyKey(String key) {
        return repo.getByKey(key);
    }

    @CachePut(value = "my_products", key = "#product.id")
    public boolean deleteProduct(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
