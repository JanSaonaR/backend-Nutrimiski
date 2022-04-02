package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.NutritionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/nutritionist")
public class NutritionistController {

    @Autowired
    NutritionistService nutritionistService;


    @GetMapping()
    public ResponseEntity<ResponseDTO<Nutritionist>> getNutritionistById(@RequestParam Long nutritionistId){
        ResponseDTO<Nutritionist> responseDTO = new ResponseDTO<>();

        try {
            Nutritionist nutritionist = nutritionistService.findById(nutritionistId);

            if (nutritionist == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("");
                responseDTO.setData(null);
            }
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(nutritionist);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activeParents")
    public ResponseEntity<ResponseDTO<List<Parent>>> getActiveParents(@RequestParam Long nutritionistId){
        ResponseDTO<List<Parent>> responseDTO = new ResponseDTO<>();

        try {
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(nutritionistService.getActiveParents(nutritionistId));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(1);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("/activeChildren")
    public ResponseEntity<ResponseDTO<List<Child>>> getActiveChildren(@RequestParam Long nutritionistId){

        ResponseDTO<List<Child>> responseDTO = new ResponseDTO<>();

        try {
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(nutritionistService.getActiveChildren(nutritionistId));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(1);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

