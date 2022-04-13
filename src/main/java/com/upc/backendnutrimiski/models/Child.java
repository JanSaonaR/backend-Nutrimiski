package com.upc.backendnutrimiski.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private LocalDate birthDate;

    @Column(length = 8)
    private String dni;

    //(F)emenino-(M)asculino
    @Column(length = 1, nullable = false)
    private String sex;

    @Column
    private String activity;

    @JsonIgnoreProperties({"children","user"})
    @ManyToOne
    @JoinColumn(name = "parentId",nullable = false)
    private Parent parent;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pictureId", nullable = true)
    private Picture picture;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy="child")
    private List<ChildLog> childLogs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChildPreferences> childPreferences = new ArrayList<>();

}
