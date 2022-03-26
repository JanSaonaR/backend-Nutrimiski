package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Meal;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/meal")
public class MealController {

    @Autowired
    MealService mealService;

    @GetMapping
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

    private List<Meal> getMealsBetweenDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                            @RequestParam Long childId) {
        return null;
    }
}
