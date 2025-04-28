package com.compartir.libros.dto.tematica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TematicaDTO {
    private String id;
    private String nombre;
    private String imagen;
    private String descripcion;
}
