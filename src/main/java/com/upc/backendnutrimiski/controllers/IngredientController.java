package com.upc.backendnutrimiski.controllers;


import com.upc.backendnutrimiski.models.Category;
import com.upc.backendnutrimiski.models.Ingredient;
import com.upc.backendnutrimiski.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @GetMapping("")
    public List<Ingredient> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return ingredientService.getAllCategories();
    }



}
