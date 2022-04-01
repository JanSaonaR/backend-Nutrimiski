package com.upc.backendnutrimiski.repositories;

import com.upc.backendnutrimiski.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {

    @Transactional
    @Modifying
    @Query("delete from Picture p where p.pictureId = ?1")
    public int deleteByPictureId(String pictureId);
}
