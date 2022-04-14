package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.NutritionalPlan;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.ChildService;
import com.upc.backendnutrimiski.services.NutritionalPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/nutritionalPlan")
public class NutritionalPlanController {

    @Autowired
    NutritionalPlanService nutritionalPlanService;

    @Autowired
    ChildService childService;

    @PostMapping
    private ResponseEntity<ResponseDTO<NutritionalPlan>> createNutritionalPlan(@RequestParam Long childId){
        ResponseDTO<NutritionalPlan> responseDTO = new ResponseDTO<>();
        try {
            Child child = childService.getChildById(childId);
            if (child == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El niño no se encuentra registrado");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(nutritionalPlanService.createNutritionalPlan(childId));
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
