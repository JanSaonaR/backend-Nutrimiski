package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Category;
import com.upc.backendnutrimiski.models.Ingredient;
import com.upc.backendnutrimiski.repositories.CategoryRepository;
import com.upc.backendnutrimiski.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    CategoryRepository  categoryRepository;

    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }



}
