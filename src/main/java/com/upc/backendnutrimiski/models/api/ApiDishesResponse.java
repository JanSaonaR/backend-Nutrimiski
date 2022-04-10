package com.upc.backendnutrimiski.models.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiDishesResponse {

    List<String> Alimento;
    List<Double> Proteinas;
    List<Double> Grasas;
    List<Double> Carbohidratos;
    List<Integer> Cantidad_Gramos_Consumir;
    List<Integer> Total_Calorias;
    List<List<String>> Ingredientes;
    List<Integer> Nivel_Preferencia;
    List<String> Image_url;
    List<String> Tipo;


}
