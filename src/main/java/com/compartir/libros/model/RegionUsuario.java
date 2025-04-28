package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionUsuario {
    private String ciudad;
    private String provincia;
    private String pais;
    private String continente;
}
