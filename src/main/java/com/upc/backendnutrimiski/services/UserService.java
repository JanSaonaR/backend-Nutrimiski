package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.models.User;
import com.upc.backendnutrimiski.models.dto.RegisterNutriotionistRequestDTO;
import com.upc.backendnutrimiski.repositories.NutritionistRepository;
import com.upc.backendnutrimiski.repositories.ParentRepository;
import com.upc.backendnutrimiski.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NutritionistRepository nutritionistRepository;

    @Autowired
    ParentRepository parentRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User registerParent(){
        User user = new User();
        return user;
    }

    public User registerNutritionist(RegisterNutriotionistRequestDTO request){
        User user = new User();
        user.setDni(request.getDni());
        user.setEmail(request.getEmail());
        user.setBirthDate(request.getBirthDate());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRegisterDate(UtilService.getNowDate());
        user.setPhone(request.getPhone());
        user.setSex(request.getSex());
        user.setPassword(UtilService.encriptarContrasena(request.getPassword()));
        user.setRol("N");

        Nutritionist nutritionist = new Nutritionist();
        nutritionist.setCollegiate(request.getCollegiate());

        try {
            user = userRepository.save(user);
            nutritionist.setUser(user);
            nutritionist  = nutritionistRepository.save(nutritionist);
        } catch (Exception e){
            e.getMessage();
        }


        return user;
    }

}
