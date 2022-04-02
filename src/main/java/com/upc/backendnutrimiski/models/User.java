package com.upc.backendnutrimiski.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long userId;

    @Column(length = 8)
    private String dni = "";

    @Column(length = 120)
    private String firstName;

    @Column(length = 120)
    private String lastName;

    @Column(length = 100)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Date birthDate;

    private String phone;

    //H-M
    @Column(length = 1, nullable = false)
    private String sex;

    private LocalDate registerDate;
    //P-N
    @Column(length = 1, nullable = false)
    private String rol;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pictureId", nullable = true)
    private Picture picture;

}
