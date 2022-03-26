package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.ChildLog;
import com.upc.backendnutrimiski.repositories.ChildLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildLogService {

    @Autowired
    ChildLogRepository childLogRepository;


    public ChildLog saveChildLog(ChildLog childLog){
        return childLogRepository.save(childLog);
    }

}
