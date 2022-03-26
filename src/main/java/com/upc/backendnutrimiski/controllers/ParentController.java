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

    @PostMapping("/child")
    public  ResponseEntity<ResponseDTO<Child>> registerChild(@RequestParam Long parentId, @RequestBody RegisterChildRequestDTO request){

        ResponseDTO<Child> responseDTO = new ResponseDTO<>();

        Parent parent = new Parent();
        try {
            parent = parentService.findById(parentId);
            if (parent == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El padre no se encuentra registrado");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(childService.registerChild(request, parent));

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
