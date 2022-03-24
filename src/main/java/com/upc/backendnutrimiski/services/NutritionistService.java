package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.repositories.NutritionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutritionistService {

    @Autowired
    NutritionistRepository nutritionistRepository;


    public Nutritionist findByUser(Long userId){
        return nutritionistRepository.findByUser(userId);
    }

    public Nutritionist saveNutritionist(Nutritionist nutritionist){
        return nutritionistRepository.save(nutritionist);
    }
}
