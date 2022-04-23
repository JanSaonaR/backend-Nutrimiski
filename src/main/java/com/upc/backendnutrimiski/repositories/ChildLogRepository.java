package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.ChildLog;
import com.upc.backendnutrimiski.models.Nutritionist;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChildLogRepository extends JpaRepository<ChildLog, Long> {

    @Query("select c from ChildLog c where c.child.childId = ?1 and c.date between  ?1 and ?2")
    public List<ChildLog> findByChildBetweenDates(Long childId, LocalDate startDate, LocalDate endDate);

}
