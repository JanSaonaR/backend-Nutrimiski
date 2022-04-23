package com.upc.backendnutrimiski.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "meal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long mealId;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "nutritionalPlanId",nullable = false)
    private NutritionalPlan nutritionalPlan;

    private LocalDate day;

    private String schedule;

    private String name;

    private Double protein;

    private Double fat;

    private Double carbohydrates;

    private String ingredients;

    private Integer totalCalories;

    private Integer gramsPortion;

    private String imageUrl;

    //0 - Created, 1 - Ok, 2 - Failed.
    private Byte status;

}