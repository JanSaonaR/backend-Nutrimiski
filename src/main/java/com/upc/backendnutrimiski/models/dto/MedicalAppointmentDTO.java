package com.upc.backendnutrimiski.models.dto;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Nutritionist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalAppointmentDTO {
    private Long childId;
    private Long nutritionistId;
}
