package com.upc.backendnutrimiski.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "childPreferences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long childPreferencesId;


    @ManyToOne
    @JoinColumn(name = "childId",nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "ingredientId",nullable = false)
    private Ingredient ingredient;

}
