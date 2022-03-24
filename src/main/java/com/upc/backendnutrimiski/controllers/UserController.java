package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.models.User;
import com.upc.backendnutrimiski.models.dto.LoginRequestDTO;
import com.upc.backendnutrimiski.models.dto.LoginResponseDTO;
import com.upc.backendnutrimiski.models.dto.RegisterNutriotionistRequestDTO;
import com.upc.backendnutrimiski.models.dto.ResponseDTO;
import com.upc.backendnutrimiski.services.NutritionistService;
import com.upc.backendnutrimiski.services.ParentService;
import com.upc.backendnutrimiski.services.UserService;
import com.upc.backendnutrimiski.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ParentService parentService;

    @Autowired
    NutritionistService nutritionistService;

    @GetMapping("")
    public String test(){
        return "hola";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request){
        LoginResponseDTO<Object> loginResponseDTO = new LoginResponseDTO<>();
        ResponseDTO<LoginResponseDTO> responseDTO = new ResponseDTO<>();

        User user = userService.findByEmail(request.getEmail());

        Integer day = 0;
        try {
            if (user != null){
                String password = UtilService.desencriptarContrasena(user.getPassword());
                if (password.equals(request.getPassword())) {
                    if (user.getRol().equals("P")){
                        Parent patient = parentService.findByUser(user.getUserId());
                        loginResponseDTO.setProfile(patient);
                        //day = mealService.getFirstDayOfWeekMeal(patient.getPatientId());
                    }else{
                        Nutritionist nutritionist = nutritionistService.findByUser(user.getUserId());
                        loginResponseDTO.setProfile(nutritionist);
                    }

                    responseDTO.setHttpCode(HttpStatus.OK.value());
                    responseDTO.setErrorCode(0);
                    responseDTO.setErrorMessage("");
                    responseDTO.setData(loginResponseDTO);


                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.set("Token", UtilService.getJWTToken(request.getEmail()));
                    responseHeaders.set("FirstDayOfWeek",  day.toString());

                    return new ResponseEntity<>(responseDTO, responseHeaders,HttpStatus.OK);
                } else {

                    responseDTO.setHttpCode(HttpStatus.OK.value());
                    responseDTO.setErrorCode(2);
                    responseDTO.setErrorMessage("Password is incorrect");
                    responseDTO.setData(null);

                    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
                }
            } else{
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("User not registered");
                responseDTO.setData(null);
            }
        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());

        }

        responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setErrorCode(4);
        responseDTO.setData(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @PostMapping("/register/parent")
    public ResponseEntity<ResponseDTO<Parent>> registerParent(){

        ResponseDTO<Parent> responseDTO = new ResponseDTO<>();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/register/nutritionist")
    public ResponseEntity<ResponseDTO<Nutritionist>> registerNutritionist(@RequestBody RegisterNutriotionistRequestDTO request){

        ResponseDTO<Nutritionist> responseDTO = new ResponseDTO<>();
        try {
            User user =
        }catch (Exception e){

        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}