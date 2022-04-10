package com.upc.backendnutrimiski.models.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiDishesResponse {

    HashMap<String,String> Alimento ;
    HashMap<String,String> Proteinas;
    HashMap<String,String> Grasas;
    HashMap<String,String> Carbohidratos;
    HashMap<String,String> Cantidad_Gramos_Consumir;
    HashMap<String,String> Total_Calorias;
    HashMap<String,String> Ingredientes;
    HashMap<String,String> Nivel_Preferencia;
    HashMap<String,String> Image_url;
    HashMap<String,String> Tipo;

}
