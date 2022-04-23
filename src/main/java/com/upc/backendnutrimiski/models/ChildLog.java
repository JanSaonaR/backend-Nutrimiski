package com.upc.backendnutrimiski.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


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

    private float weight;
    private float height;
    private int age;
    private float imc;
    private LocalDate date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "childId")
    private Child child;
}
