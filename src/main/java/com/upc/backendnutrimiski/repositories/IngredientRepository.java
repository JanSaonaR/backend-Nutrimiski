package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


}
