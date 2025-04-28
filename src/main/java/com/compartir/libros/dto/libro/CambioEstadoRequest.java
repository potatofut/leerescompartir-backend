package com.compartir.libros.dto.libro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambioEstadoRequest {
    @NotBlank(message = "El nuevo estado no puede estar vacío")
    private String nuevoEstado; // disponible, prestado, reservado
}