package com.start.ecom.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // private boolean isEmpty;
    // private double totalPrice;
    
    @OneToOne
    private Users user;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "cart",cascade = CascadeType.REMOVE)
    private List<CartItem> items; 

}
