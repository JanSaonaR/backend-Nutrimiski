package com.upc.backendnutrimiski.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterChildRequestDTO {

    private String firstName;
    private String lastName;
    private float weight;
    private float height;
    private float imc;
    private Date birthDate;
    private String dni;
    private String sex;
}
