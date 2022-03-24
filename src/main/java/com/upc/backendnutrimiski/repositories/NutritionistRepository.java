package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {

    @Query("select n from Nutritionist n where n.user.userId = ?1")
    public Nutritionist findByUser(Long userId);

}
