package com.start.ecom.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

    private ProductPage pagenation(Page<Product> pages){
        ProductPage productPage = new ProductPage(pages.getContent(),
                pages.getTotalPages(),
                pages.getNumber()+1,
                pages.getSize(),
                pages.isLast(),
                pages.isEmpty(),
                pages.getSort()
                );
        return productPage;
    }

    public ProductPage getAllProducts(PageRequest pageRequest,FilterRequest filterRequest) {

        Page<Product> page;
        if(filterRequest == null)
            page = repo.findAll(pageRequest);
        else
            page = repo.findAll(FilterBySpecification.filter(filterRequest), pageRequest);

        return pagenation(page);
    }

    //when return null value cache throws error
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

    public ProductPage getProductsbyKey(String key, PageRequest pageRequest,FilterRequest filterRequest) {
        return pagenation(repo.findAll(FilterBySpecification.searchWithFilter(key, filterRequest), pageRequest));
    }

    @CacheEvict(value = "my_products", key = "#id")
    public boolean deleteProduct(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Product updateProduct(int id, Product updatedProduct, MultipartFile imageFile) throws IOException {

        if(!repo.existsById(id))
            return null;

        return product(updatedProduct, imageFile);
    }
}
