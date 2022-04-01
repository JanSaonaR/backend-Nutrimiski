package com.upc.backendnutrimiski.services;

import com.upc.backendnutrimiski.models.Picture;
import com.upc.backendnutrimiski.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {

    @Autowired
    PictureRepository pictureRepository;

    public void deletePicture(String pictureId){
        pictureRepository.deleteByPictureId(pictureId);
    }

}
