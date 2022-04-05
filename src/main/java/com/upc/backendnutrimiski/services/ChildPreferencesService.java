package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.ChildPreferences;
import com.upc.backendnutrimiski.models.Ingredient;
import com.upc.backendnutrimiski.models.dto.IngredientPreferenceDTO;
import com.upc.backendnutrimiski.repositories.ChildPreferencesRepository;
import com.upc.backendnutrimiski.repositories.ChildRepository;
import com.upc.backendnutrimiski.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChildPreferencesService {

    @Autowired
    ChildPreferencesRepository childPreferencesRepository;

    @Autowired
    IngredientRepository ingredientRepository;


    public List<Ingredient> getListOfPreferencesByChild(Long childId){
        return  childPreferencesRepository.findByPreferencesByChild(childId);
    }

    public ChildPreferences saveListOfPreferences(List<IngredientPreferenceDTO> ingredients, Child child){

        List<ChildPreferences> childPreferences = new ArrayList<>();

        for (IngredientPreferenceDTO ingredient : ingredients){
            ChildPreferences preferences = childPreferencesRepository.findPreferencesByChildAndIngredient(child.getChildId(),ingredient.getIngredientId());
            if (preferences != null){
                return preferences;
            } else {

                preferences = new ChildPreferences();
                preferences.setChild(child);
                preferences.setIngredient(ingredientRepository.findById(ingredient.getIngredientId()).orElse(null));
                childPreferences.add(preferences);
            }
        }
        try {
            childPreferences = childPreferencesRepository.saveAll(childPreferences);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public String deleteListOfPreferences(List<IngredientPreferenceDTO> ingredients, Child child){

        List<ChildPreferences> childPreferences = new ArrayList<>();

        for (IngredientPreferenceDTO ingredient : ingredients){
            ChildPreferences preferences = childPreferencesRepository.findPreferencesByChildAndIngredient(child.getChildId(), ingredient.getIngredientId());
            if (preferences != null) {
                childPreferences.add(preferences);
            } else  {
                return "EL niño no tiene el ingrediente " + ingredient.getName() + " como favorito.";
            }
        }
        try {
            childPreferencesRepository.deleteAll(childPreferences);
            return "Alimentos favoritos elimiandos exitosamente.";
        } catch (Exception e){
            return e.getMessage();
        }

    }
}
