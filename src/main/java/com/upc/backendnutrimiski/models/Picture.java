package com.upc.backendnutrimiski.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "picture")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {

    @Id
    @Column(unique = true, nullable = false)
    private String pictureId;

    private String url = "";
}
