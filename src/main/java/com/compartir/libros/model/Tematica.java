package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo que representa una temática de libro en la base de datos.
 * Incluye información básica como nombre, imagen y descripción.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tematicas")
public class Tematica {
    @Id
    private ObjectId id;
    private String nombre;
    private String imagen;
    private String descripcion;
}
