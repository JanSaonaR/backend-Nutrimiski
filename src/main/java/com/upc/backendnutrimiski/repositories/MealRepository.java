package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Meal;
import com.upc.backendnutrimiski.models.NutritionalPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


    @Query("select m from Meal m where m.nutritionalPlan.medicalAppointment.child.childId = ?1")
    public List<Meal> getMealsByChild(Long childId);

    @Query("select m from Meal m where m.day >= ?1 and m.day <= ?2 and m.nutritionalPlan = ?3")
    public List<Meal> findMealsBetweenDatesByNutritionalPlan(Date startDate, Date endDate, NutritionalPlan nutritionalPlan);

    @Query("select m from Meal m where m.day = ?1 and m.nutritionalPlan.medicalAppointment.child.childId = ?2 and m.nutritionalPlan.isActive = 1")
    public List<Meal> findMealsByDateByChild(Date date,  Long childId);

}
