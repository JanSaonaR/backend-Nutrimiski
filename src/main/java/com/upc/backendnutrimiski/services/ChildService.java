package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.ChildLog;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.models.dto.RegisterChildRequestDTO;
import com.upc.backendnutrimiski.repositories.ChildRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.util.List;

@Service
public class ChildService {


    @Autowired
    ChildRepository childRepository;

    @Autowired
    ChildLogService childLogService;


    public List<Child> getChildrenByParent(Long parentId){
        return childRepository.findByParent(parentId);
    }

    public Child getChildById(Long childId){
        return childRepository.findById(childId).get();
    }

    public Child registerChild(RegisterChildRequestDTO request, Parent parent){

        Child child = new Child();
        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setDni(request.getDni());
        child.setHeight(request.getHeight());
        child.setWeight(request.getWeight());
        child.setImc(request.getImc());
        child.setParent(parent);
        child.setSex(request.getSex());
        child.setBirthDate(UtilService.normalizeBirthDate(request.getBirthDate()));
        child.setAge(UtilService.getActualAge(child.getBirthDate()));

        //System.out.println("Registrando niño: " + child.toString());
        child = childRepository.save(child);

        ChildLog childLog = new ChildLog();
        childLog.setChild(child);
        childLog.setHeight(child.getHeight());
        childLog.setAge(child.getAge());
        childLog.setFirstName(child.getFirstName());
        childLog.setLastName(child.getLastName());
        childLog.setImc(child.getImc());
        childLog.setWeight(child.getWeight());

        childLogService.saveChildLog(childLog);

        return child;
    }


    public Integer getRecomendedCalories(Child child){

        Double calories =  UtilService.getCaloriesForChild(child);
        Integer redonded = ((int) (calories/10))*10;

        return redonded;

    }
}
