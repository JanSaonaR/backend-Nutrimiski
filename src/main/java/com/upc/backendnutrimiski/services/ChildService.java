package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.*;
import com.upc.backendnutrimiski.models.dto.RegisterChildRequestDTO;
import com.upc.backendnutrimiski.models.dto.UpdateChildDTO;
import com.upc.backendnutrimiski.repositories.ChildRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ChildService {


    @Autowired
    ChildRepository childRepository;

    @Autowired
    ChildLogService childLogService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    PictureService pictureService;


    public List<Child> getChildrenByParent(Long parentId){
        return childRepository.findByParent(parentId);
    }

    public Child getChildById(Long childId){
        return childRepository.findById(childId).orElse(null);
    }

    public Child registerChild(RegisterChildRequestDTO request, Parent parent, MultipartFile profilePic) throws IOException {

        Child child = new Child();
        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setDni(request.getDni());
        child.setHeight(request.getHeight());
        child.setWeight(request.getWeight());
        child.setImc(request.getImc());
        child.setParent(parent);
        child.setSex(request.getSex());
        child.setBirthDate(request.getBirthDate());
        child.setAge(UtilService.getActualAge(child.getBirthDate()));


        if (profilePic != null){
            if (!profilePic.isEmpty()) {
                Map result = cloudinaryService.upload(profilePic);
                Picture picture = new Picture();
                picture.setPictureId(result.get("public_id").toString());
                picture.setUrl(result.get("url").toString());

                if (child.getPicture() != null) {
                    pictureService.deletePicture(child.getPicture().getPictureId());
                    cloudinaryService.delete(child.getPicture().getPictureId());
                }
                child.setPicture(picture);
            }
        }


        //System.out.println("Registrando niño: " + child.toString());
        child = childRepository.save(child);

        ChildLog childLog = new ChildLog();
        childLog.setChild(child);
        childLog.setHeight(child.getHeight());
        childLog.setAge(child.getAge());
        childLog.setImc(child.getImc());
        childLog.setWeight(child.getWeight());
        childLog.setDate(UtilService.getNowDate());

        childLogService.saveChildLog(childLog);


        return child;
    }

    public Integer getRecomendedCalories(Child child){

        Double calories =  UtilService.getCaloriesForChild(child);
        Integer redonded = ((int) (calories/10))*10;

        return redonded;
    }

    public Child updateChild(UpdateChildDTO request){
        Child child = getChildById(request.getChildId());
        child.setImc(request.getImc());
        child.setWeight(request.getWeight());
        child.setHeight(request.getHeight());
        child.setAge(UtilService.getActualAge(child.getBirthDate()));

        child = childRepository.save(child);

        ChildLog childLog = new ChildLog();
        childLog.setChild(child);
        childLog.setWeight(child.getWeight());
        childLog.setImc(child.getImc());
        childLog.setHeight(child.getHeight());
        childLog.setDate(UtilService.getNowDate());
        childLog.setAge(child.getAge());

        childLogService.saveChildLog(childLog);

        return child;

    }

    public String deleteChild(Long childId) {
        childRepository.deleteById(childId);
        return "El niño se elimino correctamente";
    }


    public Child subirImagen(Child child, MultipartFile profilePic) throws IOException {

        if (profilePic != null){
            if (!profilePic.isEmpty()) {
                Map result = cloudinaryService.upload(profilePic);
                Picture picture = new Picture();
                picture.setPictureId(result.get("public_id").toString());
                picture.setUrl(result.get("url").toString());

                if (child.getPicture() != null) {
                    pictureService.deletePicture(child.getPicture().getPictureId());
                    cloudinaryService.delete(child.getPicture().getPictureId());
                }
                child.setPicture(picture);
                child = childRepository.save(child);
            }
        }
        return  child;
    }

    public String deleteUserPictureProfile(Child child) throws IOException {

        if (child.getPicture() != null){
            Picture newPicture = new Picture();
            newPicture.setPictureId(null);
            newPicture.setUrl(null);

            Map result = cloudinaryService.delete(child.getPicture().getPictureId());
            child.setPicture(null);
            childRepository.save(child);//Lo borra de yapa
            return result.get("result").toString();
        }
        return "El usuario no tiene foto de perfil";
    }

}
