package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.ChildLog;
import com.upc.backendnutrimiski.repositories.ChildLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChildLogService {

    @Autowired
    ChildLogRepository childLogRepository;


    public ChildLog saveChildLog(ChildLog childLog){
        return childLogRepository.save(childLog);
    }

    public List<ChildLog> getChildLogsForChild(Long childId, LocalDate startDate, LocalDate endDate){
        return childLogRepository.findByChildBetweenDates(childId,startDate,endDate);
    }

}
