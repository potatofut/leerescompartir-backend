package com.compartir.libros.dto.libro;

import java.util.List;

import com.compartir.libros.model.Reserva;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroResponseDTO {
    private String titulo;
    private String autor;
    private String estado;
    private List<String> tematicas;
    private List<Reserva> reservas;
}
