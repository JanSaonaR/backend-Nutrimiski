package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    @Autowired
    ParentRepository parentRepository;

    public Parent findByUser(Long userId){
        return parentRepository.findByUser(userId);
    }

    public List<Child> findChildrenByParent(Long parentId){
        return parentRepository.findChildrenByParentId(parentId);
    }

    public Parent findById(Long parentId){
        return parentRepository.findById(parentId).get();
    }

}
