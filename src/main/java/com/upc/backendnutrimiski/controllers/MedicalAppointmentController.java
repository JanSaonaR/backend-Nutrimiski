package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.MedicalAppointment;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.ChildService;
import com.upc.backendnutrimiski.services.MedicalAppointmentService;
import com.upc.backendnutrimiski.services.NutritionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalAppointment")
public class MedicalAppointmentController {

    @Autowired
    MedicalAppointmentService medicalAppointmentService;

    @Autowired
    NutritionistService nutritionistService;

    @Autowired
    ChildService childService;

    @PostMapping("")
    private ResponseEntity<ResponseDTO<MedicalAppointment>> createMedicalAppointment(//@RequestParam Long nutritionistId,
                                                                                     @RequestParam Long childId){
        ResponseDTO<MedicalAppointment> responseDTO = new ResponseDTO<>();

        try {
            //Nutritionist nutritionist = nutritionistService.findById(nutritionistId);
            Nutritionist nutritionist = nutritionistService.getRandomNutritionist();
            Child child = childService.getChildById(childId);

            if (nutritionist == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El nutricionista no existe");
                responseDTO.setData(null);
            }

            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(medicalAppointmentService.createMedicalAppointment(nutritionist, child));

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);


        }catch (Exception e){

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

        }

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
