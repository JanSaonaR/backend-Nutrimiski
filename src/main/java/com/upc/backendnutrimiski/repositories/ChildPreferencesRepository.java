package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.ChildPreferences;
import com.upc.backendnutrimiski.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildPreferencesRepository extends JpaRepository<ChildPreferences, Long> {



    @Query("select c from ChildPreferences c where c.child.childId = ?1 and c.ingredient.ingredientId = ?2")
    public ChildPreferences findPreferencesByChildAndIngredient(Long childId, Long ingredientId);

    @Query("select c.ingredient from ChildPreferences c where c.child.childId = ?1")
    public List<Ingredient> findByPreferencesByChild(Long childId);


    @Query("select c.ingredient.name from ChildPreferences c where c.child.childId = ?1")
    public List<String> findPreferencesNameByChild(Long childId);

}
