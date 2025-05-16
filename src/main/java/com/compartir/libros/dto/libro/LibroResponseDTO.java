package com.compartir.libros.dto.libro;

import java.util.List;

import com.compartir.libros.model.Reserva;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO que representa la respuesta con la información de un libro.
 * Incluye los datos básicos del libro, sus temáticas y reservas.
 *
 * @author Sergio
 */
@Data
@AllArgsConstructor
public class LibroResponseDTO {
    private String titulo;
    private String autor;
    private String estado;
    private String descripcion;
    private String portada;
    private List<String> tematicas;
    private List<Reserva> reservas;
}
