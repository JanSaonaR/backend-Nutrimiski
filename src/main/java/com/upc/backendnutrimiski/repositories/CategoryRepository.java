package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
