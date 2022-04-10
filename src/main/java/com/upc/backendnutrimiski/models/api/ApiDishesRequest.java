package com.upc.backendnutrimiski.models.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiDishesRequest {

        Integer age;
        Double weight;
        Integer height;
        String activity;
        String sex;
        Integer days;
        List<String> preference;
}
