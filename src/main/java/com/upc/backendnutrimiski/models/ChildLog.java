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

    private float weight;
    private float height;
    private int age;
    private float imc;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "childId")
    private Child child;
}
