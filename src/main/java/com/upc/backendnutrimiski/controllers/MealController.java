package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Meal;
import com.upc.backendnutrimiski.models.NutritionalPlan;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.api.ApiAlternativesRequest;
import com.upc.backendnutrimiski.models.dto.ReplaceMealRequestDTO;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.MealService;
import com.upc.backendnutrimiski.services.NutritionalPlanService;
import com.upc.backendnutrimiski.services.UtilService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/meal")
public class MealController {

    @Autowired
    MealService mealService;

    @Autowired
    NutritionalPlanService nutritionalPlanService;

    @GetMapping("/all")
    private ResponseEntity<ResponseDTO<List<Meal>>> getMealsByChild(@RequestParam Long childId){

        ResponseDTO<List<Meal>> responseDTO = new ResponseDTO<>();

        try {
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(mealService.getMealsByChild(childId));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e) {
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(mealService.getMealsByChild(null));
        }


        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/day")
    private ResponseEntity<ResponseDTO<List<Meal>>> getMealsByDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam Long childId) {
        ResponseDTO<List<Meal>> responseDTO = new ResponseDTO<>();
        List<Meal> meals = new ArrayList<>();
        try {
            meals = mealService.getMealsByDay(date,childId);
            if (meals.size() == 0){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El niño no tiene dietas recomendadas para " + date);
                responseDTO.setData(meals);
            }
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(meals);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }
    }

    @GetMapping("/betweenDates")
    private ResponseEntity<ResponseDTO<List<Meal>>> getMealsBetweenDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                            @RequestParam Long childId) {



        ResponseDTO<List<Meal>> responseDTO = new ResponseDTO<>();

        try {
            NutritionalPlan nutritionalPlan = nutritionalPlanService.getActiveNutritionalPlan(childId);
            if (nutritionalPlan == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El niño no tiene dietas recomendadas");
                responseDTO.setData(null);
            }
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(mealService.getMealsBetweenDates(startDate, endDate, nutritionalPlan));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
        }
        return null;
    }

    @PutMapping("/completeMeal")
    private ResponseEntity<ResponseDTO<Meal>> completeMeal(@RequestParam Long mealId) {
        ResponseDTO<Meal> responseDTO = new ResponseDTO<>();

        try {
            Meal meal = mealService.getMealById(mealId);
            meal.setStatus((byte) 1);
            meal = mealService.saveMeal(meal);

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(meal);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.getMessage();
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/failedMeal")
    private ResponseEntity<ResponseDTO<Meal>> failedMeal(@RequestParam Long mealId) {
        ResponseDTO<Meal> responseDTO = new ResponseDTO<>();

        try {
            Meal meal = mealService.getMealById(mealId);
            meal.setStatus((byte) 2);
            meal = mealService.saveMeal(meal);

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(meal);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/restoreMeal")
    private ResponseEntity<ResponseDTO<Meal>> restoreMeal(@RequestParam Long mealId) {
        ResponseDTO<Meal> responseDTO = new ResponseDTO<>();

        try {
            Meal meal = mealService.getMealById(mealId);
            meal.setStatus((byte) 0);
            meal = mealService.saveMeal(meal);

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(meal);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.getMessage();
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/alternativeMeals")
    private ResponseEntity<ResponseDTO<List<Meal>>> getAlternativeMeals(@RequestBody ApiAlternativesRequest request) {
        ResponseDTO<List<Meal>> responseDTO = new ResponseDTO<>();

        try {
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(mealService.generateAlternativesMeal(request));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/replaceMeal")
    private ResponseEntity<ResponseDTO<Meal>> replaceAlterantiveMeal(@RequestBody ReplaceMealRequestDTO requestMeal) {

        ResponseDTO<Meal> responseDTO = new ResponseDTO<>();

        try {

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(mealService.replaceMeal(requestMeal));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
        }


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
