package com.unla.grupo_2_oo2_2020.models.structlike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ProductoAndCantidadModel {

    private String nombre;
    private int cantidad;
}