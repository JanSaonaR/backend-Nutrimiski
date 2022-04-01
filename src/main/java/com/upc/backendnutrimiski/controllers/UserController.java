package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.models.Picture;
import com.upc.backendnutrimiski.models.User;
import com.upc.backendnutrimiski.models.dto.*;
import com.upc.backendnutrimiski.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ParentService parentService;

    @Autowired
    NutritionistService nutritionistService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    PictureService pictureService;


    @GetMapping()
    public ResponseEntity<ResponseDTO<User>> getUserById(@RequestParam Long userId) {

        ResponseDTO<User> responseDTO = new ResponseDTO<>();

        try {
            User user = userService.findById(userId);
            if (user == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El usuario no existe");
                responseDTO.setData(null);

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(user);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
        }
        return new ResponseEntity<>(responseDTO , HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping(value = "/register/parent", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseDTO<Parent>> registerParent( @RequestPart(value = "profilePic",required = false) MultipartFile profilePic,
                                                               @RequestPart("request") RegisterParentRequestDTO request){

        ResponseDTO<Parent> responseDTO = new ResponseDTO<>();
        User existentUser = userService.findByEmail(request.getEmail());
        if (existentUser != null) {
            responseDTO.setErrorCode(1);
            responseDTO.setErrorMessage("El padre ya se encuentra registrado");
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        try {
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setData(userService.registerParent(request, profilePic));

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
        }
        responseDTO.setErrorCode(2);
        responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setData(null);

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/register/nutritionist", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseDTO<Nutritionist>> registerNutritionist( @RequestPart(value = "profilePic",required = false) MultipartFile profilePic,
                                                               @RequestPart("request") RegisterNutritionistRequestDTO request)
    {
       ResponseDTO<Nutritionist> responseDTO = new ResponseDTO<>();

        User existentUser = userService.findByEmail(request.getEmail());
        if (existentUser != null) {
            responseDTO.setErrorCode(1);
            responseDTO.setErrorMessage("El nutricionista ya se encuentra registrado");
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        try {
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setData(userService.registerNutritionist(request, profilePic));

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
        }
        responseDTO.setErrorCode(2);
        responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setData(null);

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/updatePassword")
    private ResponseEntity<ResponseDTO<String>> updatePassword(@RequestParam String email, @RequestParam String actualPassword, @RequestParam String newPassword) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            String result = userService.updatePassword(email, actualPassword, newPassword);
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorMessage("");
            responseDTO.setErrorCode(0);
            responseDTO.setData(result);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (Exception e){
            e.getMessage();
        }
        responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setErrorMessage("Ocurrio un problema al actualizar la contraseña");
        responseDTO.setErrorCode(1);
        responseDTO.setData(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/picture/upload")
    public ResponseEntity<ResponseDTO<User>> subirImagen(@RequestParam("profilePic")MultipartFile  profilePic, @RequestParam("userId") Long userId) throws IOException {

        ResponseDTO<User> responseDTO = new ResponseDTO<>();

        try {
            User user = userService.findById(userId);

            if (user == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setHttpCode(1);
                responseDTO.setErrorMessage("El usuario no existe");
                responseDTO.setData(null);

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setHttpCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(userService.subirImagen(user, profilePic));

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setHttpCode(2);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);

            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/picture/delete")
    public ResponseEntity<ResponseDTO<String>> deleteImagen(@RequestParam("userId") Long userId) throws IOException {

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        try {
            User user = userService.findById(userId);
            if (user == null){
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El usuario no existe");
                responseDTO.setData(null);
            }

            responseDTO.setHttpCode(HttpStatus.OK.value());
            responseDTO.setErrorCode(0);
            responseDTO.setErrorMessage("");
            responseDTO.setData(userService.deleteUserPictureProfile(userId));

        } catch (Exception e){
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(3);
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setData(null);
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
