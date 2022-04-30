package com.upc.backendnutrimiski.models.dto;

import com.upc.backendnutrimiski.models.MedicalAppointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionalPlanDTO {

    private Long nutritionalPlanId;
    private Integer caloriesPlan;
    private double weightPatient;
    private String startDate;
}
