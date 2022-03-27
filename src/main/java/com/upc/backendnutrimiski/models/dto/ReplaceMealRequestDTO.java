package com.upc.backendnutrimiski.models.dto;

import lombok.Data;

@Data
public class ReplaceMealRequestDTO {

    private Long mealId;
    private String name;
    private String ingredients;
    private double fat;
    private double protein;
    private double carbohydrates;
    private double gramsPortion;
    private double totalCalories;
    private String imageUrl;

}
