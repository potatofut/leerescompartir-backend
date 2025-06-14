package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Modelo que representa una subregión en la base de datos.
 * Incluye información sobre la región a la que pertenece y traducciones.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subregions")
public class Subregion {
    @Id
    private String id;
    
    @Field("id")
    private Integer subregionId;
    
    private String name;
    
    @Field("region_id")
    private Integer regionId;
    
    private Map<String, String> translations;
    private String wikiDataId;
}
