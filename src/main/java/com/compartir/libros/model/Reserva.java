package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Modelo que representa una reserva de libro en la base de datos.
 * Incluye las fechas de reserva, préstamo y devolución, así como el email del usuario.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    private Date fechaReserva;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private String emailUsuario;
}
