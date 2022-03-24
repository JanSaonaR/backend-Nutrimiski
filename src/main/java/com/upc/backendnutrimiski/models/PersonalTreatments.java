package com.upc.backendnutrimiski.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personalTreatment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalTreatments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long personalTreatmentId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "childId",nullable = false)
    private Child child;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "nutritionistId",nullable = false)
    private Nutritionist nutritionist;

    private Date startDate;

    private Date endDate;

    private Byte active;

}