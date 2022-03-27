package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.models.User;
import com.upc.backendnutrimiski.models.dto.RegisterNutritionistRequestDTO;
import com.upc.backendnutrimiski.models.dto.RegisterParentRequestDTO;
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

    public Parent registerParent(RegisterParentRequestDTO request){
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
        user.setRol("P");

        Parent parent = new Parent();

        try {
            user = userRepository.save(user);
            parent.setUser(user);
            parent  = parentRepository.save(parent);
        } catch (Exception e){
            e.getMessage();
        }
        return parent;
    }

    public Nutritionist registerNutritionist(RegisterNutritionistRequestDTO request){
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


        return nutritionist;
    }

    public String updatePassword(String email, String actualPassword, String newPassword){
        User user = findByEmail(email);
        if (user == null){
            return "El usuario no existe";
        }
        String actualPasswordDecrypted = UtilService.desencriptarContrasena(user.getPassword());
        if (actualPasswordDecrypted.equals(actualPassword)){
            String newPasswordEncrypted = UtilService.encriptarContrasena(newPassword);
            user.setPassword(newPasswordEncrypted);
            userRepository.save(user);
            return "La contraseña de actualizo correctamente";
        } else {
            return "La contraseña actual no es válida.";
        }
    }

}
