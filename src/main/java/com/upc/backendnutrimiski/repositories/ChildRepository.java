package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Child;
import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    @Query("select c from Child c where c.parent.parentId = ?1")
    public List<Child> findByParent(Long parentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Child c WHERE c.childId= ?1")
    public void deleteByChildId(Long childId);

    @Query("select c from Child c where c.dni = ?1")
    public Optional<Child> findByDni(String dni);

    @Query("select c.parent.children from Child c where c.childId = ?1")
    public List<Child> findBrothers(Long childId);


    @Query("select ma.nutritionist from MedicalAppointment ma where ma.child.childId = ?1 and ma.active = 1")
    public Nutritionist getActiveNutritionist(Long childId);

}
