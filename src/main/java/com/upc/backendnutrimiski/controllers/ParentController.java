package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.models.dto.RegisterChildRequestDTO;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.ChildService;
import com.upc.backendnutrimiski.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/parent")
public class ParentController {

    @Autowired
    ParentService parentService;

    @Autowired
    ChildService childService;

    @GetMapping("/children")
    public List<Child> getChildrenOfParent(@RequestParam Long parentId){
        return parentService.findChildrenByParent(parentId);
    }

    @PostMapping(value = "/registerChild", consumes = {"multipart/form-data"})
    public  ResponseEntity<ResponseDTO<Child>> registerChild(@RequestPart(value = "profilePic",required = false) MultipartFile profilePic,
                                                             @RequestParam Long parentId,
                                                             @RequestPart RegisterChildRequestDTO request){

        ResponseDTO<Child> responseDTO = new ResponseDTO<>();
        try {
            Parent parent  = parentService.findById(parentId);
            if (parent == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El padre no se encuentra registrado");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            Child child  = childService.getChildByDni(request.getDni());
            if (child != null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(2);
                responseDTO.setErrorMessage("El niño ya se encuentra registrado");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(childService.registerChild(request, parent, profilePic));

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(3);
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("")
    public ResponseEntity<ResponseDTO<Parent>> getParentById(@RequestParam Long parentId){

        ResponseDTO<Parent> responseDTO = new ResponseDTO<>();

        try {
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(parentService.findById(parentId));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(1);
            responseDTO.setData(null);
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<ResponseDTO<Child>> removeChild(@RequestParam Long childId){
        return null;
    }

}
