package com.upc.backendnutrimiski.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wrapper {
    private MultipartFile image;
    private String title;
    private String description;
}