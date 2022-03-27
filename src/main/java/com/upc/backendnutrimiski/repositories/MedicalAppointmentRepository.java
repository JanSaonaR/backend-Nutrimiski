package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.MedicalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long> {


    @Query("select m from MedicalAppointment m where m.child.childId = ?1 and m.active = 1")
    public MedicalAppointment findActiveMedicalAppointmentByChild(Long childId);

}