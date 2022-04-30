package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Meal;
import com.upc.backendnutrimiski.models.NutritionalPlan;
import com.upc.backendnutrimiski.models.api.ApiAlternativesRequest;
import com.upc.backendnutrimiski.models.api.ApiDishesRequest;
import com.upc.backendnutrimiski.models.dto.ReplaceMealRequestDTO;
import com.upc.backendnutrimiski.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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


        String url = "https://apidieta.azurewebsites.net/api/v1.0/diets/refoods";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ApiAlternativesRequest apiRequest = new ApiAlternativesRequest();
        apiRequest.setType(request.getType());
        apiRequest.setCalories(request.getCalories());


        HttpEntity<ApiAlternativesRequest> httpEntity = new HttpEntity<>(apiRequest, headers);

        HashMap<String, HashMap<String, Object>> dishes = restTemplate.postForObject(url, httpEntity, HashMap.class);
        String index= "0";
        for (int i = 0; i < 3; i++){
            index = Integer.toString(i);
            Meal meal = new Meal();
            meal.setName                    ((String) dishes.get("Alimento").get(index));
            meal.setProtein                 ((Double) dishes.get("Proteinas").get(index));
            meal.setFat                     ((Double) dishes.get("Grasas").get(index));
            meal.setCarbohydrates           ((Double) dishes.get("Carbohidratos").get(index));
            meal.setGramsPortion            ((Integer) dishes.get("Cantidad_Gramos_Consumir").get(index));
            meal.setSchedule                ((String) dishes.get("Tipo").get(index));
            meal.setStatus((byte) 0);
            String result = String.join("-", ((ArrayList<String>) dishes.get("Ingredientes").get(index)));
            meal.setIngredients(result);
            meal.setImageUrl                ((String) dishes.get("Image_url").get(index));
            meal.setTotalCalories           ((Integer) dishes.get("Total_Calorias").get(index));
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
        originalMeal.setGramsPortion((int) requestMeal.getGramsPortion());
        originalMeal.setImageUrl(requestMeal.getImageUrl());
        originalMeal.setTotalCalories((int) requestMeal.getTotalCalories());

        return mealRepository.save(originalMeal);
    }

}
