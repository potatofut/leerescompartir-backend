package com.compartir.libros.dto.libro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.compartir.libros.model.Reserva;

/**
 * DTO que representa un libro con toda su información detallada.
 * Incluye información del libro, sus temáticas, reservas y datos del usuario propietario.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
    private String titulo;
    private String autor;
    private String descripcion;
    private String portada;
    private String estado;
    private List<String> tematicas;
    private List<Reserva> reservas;
    private String emailUsuario;
    private String ciudadUsuario;
    private String provinciaUsuario;
    private String paisUsuario;
}