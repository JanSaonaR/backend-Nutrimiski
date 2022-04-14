package com.upc.backendnutrimiski.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "medicalAppointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medicalAppointmentId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "childId",nullable = false)
    private Child child;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "nutritionistId",nullable = false)
    private Nutritionist nutritionist;

    private LocalDate startDate;

    private LocalDate endDate;

    private Byte active;

    @JsonIgnore
    @OneToMany(mappedBy = "medicalAppointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NutritionalPlan> nutritionalPlans = new ArrayList<>();

}