package com.upc.backendnutrimiski.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "child")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Child implements Serializable {

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

    private Date birthDate;

    @Column(length = 8)
    private String dni;

    //H-M
    @Column(length = 1, nullable = false)
    private String sex;

    @JsonIgnoreProperties({"children","user"})
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",nullable = false)
    private Parent parent;
}
