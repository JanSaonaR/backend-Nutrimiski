package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.*;
import com.upc.backendnutrimiski.repositories.NutritionalPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NutritionalPlanService {

    @Autowired
    NutritionalPlanRepository nutritionalPlanRepository;

    @Autowired
    MedicalAppointmentService medicalAppointmentService;

    @Autowired
    ChildService childService;

    @Autowired
    MealService mealService;

    @Autowired
    NutritionistService nutritionistService;


    public NutritionalPlan getActiveNutritionalPlan(Long childId){
        return nutritionalPlanRepository.getActiveNutritionalPlanByChild(childId);
    }

    public NutritionalPlan createNutritionalPlan(Long childId){

        Child child = childService.getChildById(childId);
        MedicalAppointment medicalAppointment = medicalAppointmentService.getActiveMedicalAppointment(childId);

        NutritionalPlan nutritionalPlan = new NutritionalPlan();

        if (medicalAppointment != null){
            nutritionalPlan.setMedicalAppointment(medicalAppointment);
        } else {
            medicalAppointment = new MedicalAppointment();
            medicalAppointment.setActive((byte) 1);
            medicalAppointment.setEndDate(null);
            medicalAppointment.setChild(child);
            medicalAppointment.setStartDate(UtilService.getNowDate());
            medicalAppointment.setNutritionist(nutritionistService.getFamilyNutritionist(child));
            medicalAppointment = medicalAppointmentService.saveMedicalAppointment(medicalAppointment);
        }

        NutritionalPlan last = nutritionalPlanRepository.getActiveNutritionalPlanByChild(childId);
        if (last!=null){
            last.setIsActive((byte) 0);
            nutritionalPlanRepository.save(last);
        }
        nutritionalPlan.setIsActive((byte) 1);
        nutritionalPlan.setCaloriesPlan(childService.getRecomendedCalories(child));
        nutritionalPlan.setWeightPatient(child.getWeight());
        nutritionalPlan.setMedicalAppointment(medicalAppointment);
        nutritionalPlan =  nutritionalPlanRepository.save(nutritionalPlan);

        System.out.println("Generando comidas...");
        generateMeals(nutritionalPlan);
        System.out.println("Comidas generadas...");

        Nutritionist nutritionist = medicalAppointment.getNutritionist();
        nutritionist.setActiveChildren(nutritionistService.getTotalActiveChildren(nutritionist.getNutritionistId()));
        nutritionistService.saveNutritionist(nutritionist);

        return nutritionalPlan;
    }

    public List<Meal> generateMeals(NutritionalPlan nutritionalPlan){
        Integer calories = nutritionalPlan.getCaloriesPlan();
        Integer weight = (int) nutritionalPlan.getWeightPatient();
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i< 15; i++){
            for (int j = 0; j< 3; j++) {
                Meal meal = new Meal();
                meal.setDay(UtilService.getNowDateMealsWhitAddDays(i + 1));
                meal.setFat(30);
                meal.setProtein(150);
                meal.setCarbohydrates(500);
                meal.setGramsPortion(300);
                meal.setImageUrl("");
                meal.setIngredients("Platano-Platano2");
                meal.setName("Comida " + j);
                meal.setStatus((byte) 0);
                meal.setTotalCalories(500);
                meal.setSchedule("DESAYUNO");
                meal.setNutritionalPlan(nutritionalPlan);
                meals.add(mealService.saveMeal(meal));
            }
        }
        return meals;
    }

}
