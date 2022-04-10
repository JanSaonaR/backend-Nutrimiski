package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.*;
import com.upc.backendnutrimiski.models.api.ApiDishesRequest;
import com.upc.backendnutrimiski.models.api.ApiDishesResponse;
import com.upc.backendnutrimiski.repositories.NutritionalPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    ChildPreferencesService childPreferencesService;


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

    public List<Meal> generateMeals(NutritionalPlan nutritionalPlan) {

        String url = "https://dieta-api.herokuapp.com/api/v1.0/diets";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Child child = nutritionalPlan.getMedicalAppointment().getChild();

        ApiDishesRequest apiRequest = new ApiDishesRequest();
        apiRequest.setAge(child.getAge());
        apiRequest.setWeight((int) child.getWeight());
        apiRequest.setHeight((int) child.getHeight());
        apiRequest.setSex(child.getSex());
        apiRequest.setDays(30);
        apiRequest.setActivity(child.getActivity() == null ? "sedentario" : child.getActivity());
        List<String> preferences = new ArrayList<>();
        // preferences  = childPreferencesService.getListOfPreferencesNameByChild(child.getChildId());
        apiRequest.setPreference(preferences);

        System.out.println(apiRequest);
        HttpEntity<ApiDishesRequest> httpEntity = new HttpEntity<>(apiRequest, headers);
        System.out.println(httpEntity);

        HashMap<String, HashMap<String, Object>> dishes = restTemplate.postForObject(url, httpEntity, HashMap.class);

        //ApiDishesResponse dishes = restTemplate.postForObject(url, httpEntity, ApiDishesResponse.class);

        List<Meal> meals = new ArrayList<>();

        System.out.println(dishes.get("Alimento"));
        System.out.println(dishes.get("Alimento").get("0"));
        String index = "0";
        Integer d = 0;
        Integer a = apiRequest.getDays() -1;
        Integer c = apiRequest.getDays()*2 -1;

        for (int i = 0; i < apiRequest.getDays(); i++){
            for (int j = 0; j< 3; j++){
                index = Integer.toString(i + (j * apiRequest.getDays()));
                Meal meal = new Meal();
                meal.setName                    ((String) dishes.get("Alimento").get(index));
                meal.setProtein                 ((Double) dishes.get("Proteinas").get(index));
                meal.setFat                     ((Double) dishes.get("Grasas").get(index));
                meal.setCarbohydrates           ((Double) dishes.get("Carbohidratos").get(index));
                meal.setGramsPortion            ((Integer) dishes.get("Cantidad_Gramos_Consumir").get(index));
                //(dishes.get("Nivel_Preferencia").get(i));
                meal.setSchedule                ((String) dishes.get("Tipo").get(index));
                meal.setDay(UtilService.getNowDateMealsWhitAddDays((i/3) + 1));
                meal.setStatus((byte) 0);
                meal.setNutritionalPlan(nutritionalPlan);
                String result = String.join("-", ((ArrayList<String>) dishes.get("Ingredientes").get(index)));
                meal.setIngredients(result);
                meal.setImageUrl                ((String) dishes.get("Image_url").get(index));
                meal.setTotalCalories           ((Integer) dishes.get("Total_Calorias").get(index));
                meals.add(meal);
            }
//            index = Integer.toString(i);
//            Meal meal = new Meal();
//            meal.setName                    ((String) dishes.get("Alimento").get(index));
//            meal.setProtein                 ((Double) dishes.get("Proteinas").get(index));
//            meal.setFat                     ((Double) dishes.get("Grasas").get(index));
//            meal.setCarbohydrates           ((Double) dishes.get("Carbohidratos").get(index));
//            meal.setGramsPortion            ((Integer) dishes.get("Cantidad_Gramos_Consumir").get(index));
//            //(dishes.get("Nivel_Preferencia").get(i));
//            meal.setSchedule                ((String) dishes.get("Tipo").get(index));
//            meal.setDay(UtilService.getNowDateMealsWhitAddDays((i/3) + 1));
//            meal.setStatus((byte) 0);
//            meal.setNutritionalPlan(nutritionalPlan);
//            String result = String.join("-", ((ArrayList<String>) dishes.get("Ingredientes").get(index)));
//            meal.setIngredients(result);
//            meal.setImageUrl                ((String) dishes.get("Image_url").get(index));
//            meal.setTotalCalories           ((Integer) dishes.get("Total_Calorias").get(index));
//            meals.add(meal);
        }
        return mealService.saveListOfMeals(meals);
    }

    public List<Meal> generateMealsTest(NutritionalPlan nutritionalPlan){
        Integer calories = nutritionalPlan.getCaloriesPlan();
        Integer weight = (int) nutritionalPlan.getWeightPatient();
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i< 15; i++){
            for (int j = 0; j< 3; j++) {
                Meal meal = new Meal();
                meal.setDay(UtilService.getNowDateMealsWhitAddDays(i + 1));
                meal.setFat(30.);
                meal.setProtein(150.);
                meal.setCarbohydrates(500.);
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
