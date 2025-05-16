package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo que representa una región o continente en la base de datos.
 * Incluye traducciones y referencias a datos externos.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "regions")
public class Region {
    @Id
    private Integer id;
    private String name;
    private Map<String, String> translations;
    private String wikiDataId;
}
