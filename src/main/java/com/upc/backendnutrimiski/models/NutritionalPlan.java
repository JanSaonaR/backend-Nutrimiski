package com.upc.backendnutrimiski.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "nutritionalPlan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionalPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long nutritionalPlanId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "medicalAppointmentId",nullable = false)
    private MedicalAppointment medicalAppointment;

    private Integer caloriesPlan;

    private double weightPatient;

    private Byte isActive;

}