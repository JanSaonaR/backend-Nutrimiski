package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.ChildLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildLogRepository extends JpaRepository<ChildLog, Long> {

}
