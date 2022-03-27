package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {

    @Query("select n from Nutritionist n where n.user.userId = ?1")
    public Nutritionist findByUser(Long userId);

    @Query(value = "SELECT parent_id FROM parent WHERE parent_id in (SELECT ch.parent_id FROM medical_appointment ma JOIN child ch ON ma.child_id = ch.child_id WHERE ma.active = 1 AND ma.nutritionist_id=?1 GROUP BY ch.parent_id);", nativeQuery = true)
    public List<Long> findActivesParents(Long nutritionistId);

    @Query("select m.child from MedicalAppointment m where m.nutritionist.nutritionistId = ?1 and m.active = 1")
    public List<Child> findActiveChildren(Long nutritionistId);

}
