package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    @Query("select c from Child c where c.parent.parentId = ?1")
    public List<Child> findByParent(Long parentId);


}
