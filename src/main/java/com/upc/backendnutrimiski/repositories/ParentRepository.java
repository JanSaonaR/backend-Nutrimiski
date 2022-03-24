package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {

    @Query("select p from Parent p where p.user.userId = ?1")
    public Parent findByUser(Long userId);
}
