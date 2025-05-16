package com.compartir.libros.dto.libro;

import lombok.Data;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * DTO para la solicitud de creación o actualización de un libro.
 * Incluye validaciones para los campos obligatorios.
 *
 * @author Sergio
 */
@Data
public class LibroRequestDTO {
    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;
    
    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;

    private String descripcion;

    private String portada;
    
    @NotEmpty(message = "Debe seleccionar al menos una temática")
    private List<String> tematicas;
}
