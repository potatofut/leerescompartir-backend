package com.compartir.libros.dto.libro;

import lombok.Data;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Data
public class LibroRequestDTO {
    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;
    
    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;
    
    @NotEmpty(message = "Debe seleccionar al menos una temática")
    private List<String> tematicas;
}
