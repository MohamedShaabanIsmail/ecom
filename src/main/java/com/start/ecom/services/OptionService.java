package com.start.ecom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.start.ecom.models.Option;
import com.start.ecom.repositores.OptionRepo;
import com.start.ecom.repositores.OptionValueRepo;

@Service
public class OptionService {
    
    @Autowired
    private OptionRepo optionRepo;
    
    @Autowired
    private OptionValueRepo valueRepo;

    public void addOption(Option option) {
        Option savedOption = optionRepo.save(option);
        option.getOptionValues().forEach(value -> {
            value.setOption(savedOption);
            valueRepo.save(value);
        });
    }

    public boolean deleteOptionValue(int id) {
        if(valueRepo.existsById(id)) {
            valueRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteOption(int id) {
        if(optionRepo.existsById(id)) {
            optionRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
