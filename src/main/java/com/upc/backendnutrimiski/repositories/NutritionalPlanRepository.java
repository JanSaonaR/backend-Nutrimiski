package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.NutritionalPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionalPlanRepository extends JpaRepository<NutritionalPlan, Long> {

    @Query("select n from NutritionalPlan n where n.medicalAppointment.child.childId = ?1 and n.isActive = 1")
    public NutritionalPlan getActiveNutritionalPlanByChild(Long childId);

}
