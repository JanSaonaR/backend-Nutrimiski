package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Meal;
import com.upc.backendnutrimiski.models.NutritionalPlan;
import com.upc.backendnutrimiski.models.api.ApiAlternativesRequest;
import com.upc.backendnutrimiski.models.dto.ReplaceMealRequestDTO;
import com.upc.backendnutrimiski.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Meal> saveListOfMeals(List<Meal> meals){
        return mealRepository.saveAll(meals);
    }


    public List<Meal> getMealsByDay(LocalDate date, Long patientId){
        return  mealRepository.findMealsByDateByChild(date,patientId);
    }
    public List<Meal> getMealsBetweenDates(LocalDate startDate, LocalDate endDate, NutritionalPlan nutritionalPlan){
        List<Meal> meals = mealRepository.findMealsBetweenDatesByNutritionalPlan(startDate,endDate,nutritionalPlan );
        return meals;
    }

    public List<Meal> getMealsByChild(Long childId){
        List<Meal> meals = mealRepository.getMealsByChild(childId);
        return meals;
    }

    public Meal getMealById(Long mealId){
        return mealRepository.findById(mealId).get();
    }

    public List<Meal> generateAlternativesMeal(ApiAlternativesRequest request){
        List<Meal> meals = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            Meal meal = new Meal();
            meal.setDay(UtilService.getNowDateMealsWhitAddDays(i+1));
            meal.setFat(20 + i);
            meal.setProtein(150 + i);
            meal.setCarbohydrates(500 + i);
            meal.setGramsPortion(300 + i);
            meal.setImageUrl("");
            meal.setIngredients("Platano " + i);
            meal.setName("Alternativa "+ i);
            meal.setStatus((byte) 0);
            meal.setTotalCalories(500 + i);
            meal.setSchedule("DESAYUNO");
            meals.add(meal);
        }
        return meals;


    }

    public Meal replaceMeal(ReplaceMealRequestDTO requestMeal){

        Meal originalMeal = getMealById(requestMeal.getMealId());

        originalMeal.setName(requestMeal.getName());
        originalMeal.setIngredients(requestMeal.getIngredients());
        originalMeal.setFat(requestMeal.getFat());
        originalMeal.setProtein(requestMeal.getProtein());
        originalMeal.setCarbohydrates(requestMeal.getCarbohydrates());
        originalMeal.setGramsPortion(requestMeal.getGramsPortion());
        originalMeal.setImageUrl(requestMeal.getImageUrl());
        originalMeal.setTotalCalories(requestMeal.getTotalCalories());

        return mealRepository.save(originalMeal);
    }

}
