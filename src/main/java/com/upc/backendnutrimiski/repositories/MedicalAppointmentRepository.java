package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.MedicalAppointment;
import com.upc.backendnutrimiski.models.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long> {


    @Query("select m from MedicalAppointment m where m.child.childId = ?1 and m.active = 1")
    public MedicalAppointment findActiveMedicalAppointmentByChild(Long childId);


    @Query("select count(m) from MedicalAppointment m where m.nutritionist.nutritionistId = ?1 and m.active = 1")
    public Integer findTotalActiveChildren(Long nutritionistId);

    @Query(value = "SELECT ma.nutritionist_id FROM medical_appointment ma JOIN child c ON ma.child_id= c.child_id WHERE c.child_id IN ?1 LIMIT 1", nativeQuery = true)
    public Long findFamilyNutritionist(List<Long> children);

}
