package com.upc.backendnutrimiski.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "childLog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long childLogId;

    @Column(length = 120)
    private String firstName;

    @Column(length = 120)
    private String lastName;

    private float weight;
    private float height;
    private int age;
    private float imc;

    private Date date;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "childId",nullable = false)
    private Child child;
}
