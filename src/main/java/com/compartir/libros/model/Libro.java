package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private String titulo;
    private String autor;
    private String descripcion;
    private String portada;
    private String estado; // disponible, prestado, reservado
    private List<ObjectId> tematicas;
    private List<Reserva> reservas;
}
