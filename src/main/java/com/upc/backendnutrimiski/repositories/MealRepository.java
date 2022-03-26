package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


    @Query("select m from Meal m where m.nutritionalPlan.medicalAppointment.child.childId = ?1")
    public List<Meal> getMealsByChild(Long childId);

}
