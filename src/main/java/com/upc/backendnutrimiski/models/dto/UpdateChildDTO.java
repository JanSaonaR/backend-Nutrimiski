package com.upc.backendnutrimiski.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChildDTO {

    private Long childId;
    private float weight;
    private float height;
    private float imc;
}
