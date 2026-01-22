package com.start.ecom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.ecom.services.OptionService;

@RestController
@RequestMapping("/option")
public class OptionController {
    
    @Autowired
    private OptionService optionService;

    @DeleteMapping("/delete_value/{id}")
    public ResponseEntity<?> deleteOptionValue(@PathVariable int id) {
        try {
            if (optionService.deleteOptionValue(id)) 
                return new ResponseEntity<>(HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting option value: " + e.getMessage());
        }
        
    }

    @DeleteMapping("/delete_option/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable int id) {
        try {
            if (optionService.deleteOption(id)) 
                return new ResponseEntity<>(HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting option : " + e.getMessage());
        }
    }
}
