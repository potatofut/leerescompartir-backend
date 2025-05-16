package com.compartir.libros.dto.tematica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una temática de libro.
 * Contiene la información básica de la temática incluyendo su imagen y descripción.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TematicaDTO {
    private String id;
    private String nombre;
    private String imagen;
    private String descripcion;
}
