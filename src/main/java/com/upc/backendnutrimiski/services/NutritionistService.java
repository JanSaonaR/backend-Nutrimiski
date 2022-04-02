package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.repositories.NutritionistRepository;
import com.upc.backendnutrimiski.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class NutritionistService {

    @Autowired
    NutritionistRepository nutritionistRepository;

    @Autowired
    ParentRepository parentRepository;


    public Nutritionist findById(Long nutritionistId){
        return nutritionistRepository.findById(nutritionistId).orElse(null);
    }

    public Nutritionist findByUser(Long userId){
        return nutritionistRepository.findByUser(userId);
    }

    public Nutritionist saveNutritionist(Nutritionist nutritionist){
        return nutritionistRepository.save(nutritionist);
    }

    public List<Child> getChildren(){
        return null;
    }

    public List<Parent> getActiveParents(Long nutritionistId){
        List<Long> ids = nutritionistRepository.findActivesParents(nutritionistId);
        List<Parent> parents;
        if (ids != null){
            parents = parentRepository.findByParentIdIn(ids);
            return  parents;
        }
        return null;
    }

    public List<Child> getActiveChildren(Long nutritionistId){
        return nutritionistRepository.findActiveChildren(nutritionistId);
    }

    public  Nutritionist getRandomNutritionist(){
        List<Nutritionist> nutritionists = nutritionistRepository.findAll();
        Random rand = new Random();
        int rand_int = rand.nextInt(nutritionists.size());
        return nutritionists.get(rand_int);
    }
}
