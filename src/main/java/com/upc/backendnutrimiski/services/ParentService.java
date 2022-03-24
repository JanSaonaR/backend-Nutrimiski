package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Parent;
import com.upc.backendnutrimiski.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService {

    @Autowired
    ParentRepository parentRepository;

    public Parent findByUser(Long userId){
        return parentRepository.findByUser(userId);
    }

}
