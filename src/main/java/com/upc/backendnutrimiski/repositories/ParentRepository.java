package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {

    @Query("select p from Parent p where p.user.userId = ?1")
    public Parent findByUser(Long userId);

    @Query("select p.children from Parent p where p.parentId = ?1")
    public List<Child> findChildrenByParentId(Long parentId);

    @Query("select p from Parent p where p.parentId in ?1")
    public List<Parent> findByParentIdIn(List<Long> parentIds);
}
