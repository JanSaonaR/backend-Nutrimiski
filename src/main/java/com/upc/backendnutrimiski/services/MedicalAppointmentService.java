package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.MedicalAppointment;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.repositories.MedicalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalAppointmentService {

    @Autowired
    MedicalAppointmentRepository medicalAppointmentRepository;



    public MedicalAppointment getActiveMedicalAppointment(Long childId){
        MedicalAppointment medicalAppointment = medicalAppointmentRepository.findActiveMedicalAppointmentByChild(childId);
        return medicalAppointment;
    }

    public MedicalAppointment saveMedicalAppointment(MedicalAppointment medicalAppointment) {
        return medicalAppointmentRepository.save(medicalAppointment);
    }

    public MedicalAppointment createMedicalAppointment(Nutritionist nutritionist, Child child){
        MedicalAppointment last = getActiveMedicalAppointment(child.getChildId());
        if (last != null)
        {
            last.setActive((byte) 0);
            last.setEndDate(UtilService.getNowDate());
            medicalAppointmentRepository.save(last);
        }

        MedicalAppointment medicalAppointment = new MedicalAppointment();
        medicalAppointment.setActive((byte)1);
        medicalAppointment.setStartDate(UtilService.getOnlyNowDate());
        medicalAppointment.setEndDate(null);
        medicalAppointment.setNutritionist(nutritionist);
        medicalAppointment.setChild(child);

        return medicalAppointmentRepository.save(medicalAppointment);
    }



}
