package com.compartir.libros.dto.libro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.compartir.libros.model.Reserva;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
    private String titulo;
    private String autor;
    private String estado;
    private List<String> tematicas;
    private List<Reserva> reservas;
    private String nombreUsuario;
    private String ciudadUsuario;
    private String provinciaUsuario;
    private String paisUsuario;
}