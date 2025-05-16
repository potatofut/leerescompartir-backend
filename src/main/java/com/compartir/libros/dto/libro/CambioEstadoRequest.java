package com.compartir.libros.dto.libro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para solicitar el cambio de estado de un libro.
 * Los estados posibles son: disponible, prestado, reservado.
 *
 * @author Sergio
 */
@Data
public class CambioEstadoRequest {
    @NotBlank(message = "El nuevo estado no puede estar vacío")
    private String nuevoEstado; // disponible, prestado, reservado
}