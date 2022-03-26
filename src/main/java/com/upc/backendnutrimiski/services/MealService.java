package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Meal;
import com.upc.backendnutrimiski.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MealService {

    @Autowired
    MealRepository mealRepository;

    public Meal saveMeal(Meal meal){
        return mealRepository.save(meal);
    }

    public List<Meal> getMealsBetweenDates(Date startDate, Date endDate, Long childId){
        List<Meal> meals = new ArrayList<>();

        return null;
    }


    public List<Meal> getMealsByChild(Long childId){
        List<Meal> meals = mealRepository.getMealsByChild(childId);
        return meals;
    }


}
