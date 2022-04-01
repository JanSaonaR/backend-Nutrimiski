package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.models.dto.UpdateChildDTO;
import com.upc.backendnutrimiski.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/child")
public class ChildController {

    @Autowired
    ChildService childService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<Child>> getChildById(@RequestParam Long childId){

        ResponseDTO<Child> responseDTO = new ResponseDTO<>();

        try {
            Child child = childService.getChildById(childId);
            if (child == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("No existe el niño");
                responseDTO.setData(null);
            }
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(child);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Child>> updateChild(@RequestBody UpdateChildDTO request){
        ResponseDTO<Child> responseDTO = new ResponseDTO<>();

        try {
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(childService.updateChild(request));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
        }
        responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setErrorCode(1);
        responseDTO.setData(null);

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseDTO<String>> deleteChild(@RequestParam Long childId){

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            Child child = childService.getChildById(childId);
            if (child == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("No existe el niño");
                responseDTO.setData(null);
            }

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(childService.deleteChild(childId));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
