package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    private Date fechaReserva;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private String emailUsuario;
}
