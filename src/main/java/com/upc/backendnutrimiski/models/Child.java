package com.upc.backendnutrimiski.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "child")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long childId;

    @Column(length = 120)
    private String firstName;

    @Column(length = 120)
    private String lastName;

    private float weight;
    private float height;
    private int age;

    private float imc;


    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",nullable = false)
    private Parent parent;
}
