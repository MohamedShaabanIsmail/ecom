package com.start.ecom.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.start.ecom.DTOs.FilterRequest;
import com.start.ecom.models.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class FilterBySpecification {
    
    public static Specification<Product> filter (FilterRequest filterRequest) {
        return new Specification<Product>() {

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();

                if(filterRequest.getBrand() != null)
                    predicate.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), filterRequest.getBrand().toLowerCase()));
                
                if(filterRequest.getCategory() != null)
                    predicate.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("category")), filterRequest.getCategory().toLowerCase()));

                if(filterRequest.getMaxPrice() != null)
                    predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filterRequest.getMaxPrice()));

                if(filterRequest.getMinPrice() != null)
                    predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filterRequest.getMinPrice()));

                return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
            }
            
        };
    }

    public static Specification<Product> searchWithFilter(String key, FilterRequest filterRequest){
        return new Specification<Product>(){

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(
                    criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + key.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + key.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + key.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + key.toLowerCase() + "%")
                ));

                if(filterRequest != null){

                    if(filterRequest.getBrand() != null)
                        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), filterRequest.getBrand().toLowerCase()));
                    
                    if(filterRequest.getCategory() != null)
                        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("category")), filterRequest.getCategory().toLowerCase()));

                    if(filterRequest.getMaxPrice() != null)
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filterRequest.getMaxPrice()));

                    if(filterRequest.getMinPrice() != null)
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filterRequest.getMinPrice()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

        };
    }

}
