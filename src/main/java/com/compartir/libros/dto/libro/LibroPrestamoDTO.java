package com.compartir.libros.dto.libro;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un libro en estado de préstamo.
 * Contiene la información del libro y las fechas relacionadas con el préstamo.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroPrestamoDTO {
    private String titulo;
    private String autor;
    private String descripcion;
    private String portada;
    private String estado; // disponible, prestado, reservado
    private String emailUsuario;
    private Date fechaReserva;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
}
