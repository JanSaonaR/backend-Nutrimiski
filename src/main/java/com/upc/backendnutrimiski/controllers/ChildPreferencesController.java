package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.ChildPreferences;
import com.upc.backendnutrimiski.models.Ingredient;
import com.upc.backendnutrimiski.models.dto.IngredientPreferenceDTO;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.ChildPreferencesService;
import com.upc.backendnutrimiski.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/childPreferences")
public class ChildPreferencesController {

    @Autowired
    ChildPreferencesService childPreferencesService;

    @Autowired
    ChildService childService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO<String>> registerListOfChildPreferences(@RequestBody List<IngredientPreferenceDTO> ingredients, @RequestParam Long childId){

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            Child child = childService.getChildById(childId);
            if (child == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El niño no existe");
                responseDTO.setData(null);

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            ChildPreferences childPreferences = childPreferencesService.saveListOfPreferences(ingredients, child);
            if (childPreferences != null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(0);
                responseDTO.setErrorMessage("");
                responseDTO.setData("El ingrediente "+ childPreferences.getIngredient().getName() + " ya se encuentra en los favoritos");

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData("¡Ingredientes favoritos guardados exitosamente!");

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO<String>> deleteListOfChildPreferences(@RequestBody List<IngredientPreferenceDTO> ingredients, @RequestParam Long childId){

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            Child child = childService.getChildById(childId);
            if (child == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El niño no existe");
                responseDTO.setData(null);

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(childPreferencesService.deleteListOfPreferences(ingredients, child));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<Ingredient>>> getListOfChildPreferences(@RequestParam Long childId){

        ResponseDTO<List<Ingredient>> responseDTO = new ResponseDTO<>();

        try {

            Child child = childService.getChildById(childId);

            if (child == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El niño no se encuetra registrado");
                responseDTO.setData(null);

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(childPreferencesService.getListOfPreferencesByChild(childId));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
