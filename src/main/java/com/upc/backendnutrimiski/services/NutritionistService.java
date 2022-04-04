package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.repositories.ChildRepository;
import com.upc.backendnutrimiski.repositories.MedicalAppointmentRepository;
import com.upc.backendnutrimiski.repositories.NutritionistRepository;
import com.upc.backendnutrimiski.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class NutritionistService {

    @Autowired
    NutritionistRepository nutritionistRepository;

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    MedicalAppointmentRepository medicalAppointmentRepository;

    @Autowired
    ChildRepository childRepository;

    public Nutritionist findById(Long nutritionistId){
        return nutritionistRepository.findById(nutritionistId).orElse(null);
    }

    public Nutritionist findByUser(Long userId){
        return nutritionistRepository.findByUser(userId);
    }

    public Nutritionist saveNutritionist(Nutritionist nutritionist){
        return nutritionistRepository.save(nutritionist);
    }

    public List<Child> getChildren(){
        return null;
    }

    public List<Parent> getActiveParents(Long nutritionistId){
        List<Long> ids = nutritionistRepository.findActivesParents(nutritionistId);
        List<Parent> parents;
        if (ids != null){
            parents = parentRepository.findByParentIdIn(ids);
            return  parents;
        }
        return null;
    }

    public List<Child> getActiveChildren(Long nutritionistId){
        return nutritionistRepository.findActiveChildren(nutritionistId);
    }

    public  Nutritionist getRandomNutritionist(){
        List<Nutritionist> nutritionists = nutritionistRepository.findAll();
        Random rand = new Random();
        int rand_int = rand.nextInt(nutritionists.size());
        return nutritionists.get(rand_int);
    }

    public  Nutritionist getFamilyNutritionist(Child child){
        List<Child> children = childRepository.findBrothers(child.getChildId());
        List<Long> childrenIds = new ArrayList<>();
        if (children.size() > 1){
            for (Child childAux: children) {
                childrenIds.add(childAux.getChildId());
            }
            Long nutritionistId = medicalAppointmentRepository.findFamilyNutritionist(childrenIds);
            return findById(nutritionistId);
        }
        else{
            return getRandomNutritionist();
        }
    }

    public Integer getTotalActiveChildren(Long nutritionistId){
        return medicalAppointmentRepository.findTotalActiveChildren(nutritionistId);
    }
}
