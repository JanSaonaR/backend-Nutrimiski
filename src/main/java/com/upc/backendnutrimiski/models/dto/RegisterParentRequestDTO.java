package com.upc.backendnutrimiski.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterParentRequestDTO {

    private String dni = "";
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birthDate;
    private String phone;
    private String sex;

}
