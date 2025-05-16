package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Modelo que representa una provincia o estado en la base de datos.
 * Incluye información geográfica y referencia a su país.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "states")
public class Provincia {
    @Id
    private Integer id;
    private String name;
    
    @Field("country_id")
    private Integer countryId;
    
    @Field("country_code")
    private String countryCode;
    
    @Field("country_name")
    private String countryName;
    
    @Field("state_code")
    private String stateCode;
    
    private String type;
    private String latitude;
    private String longitude;
    
    @DBRef
    private Pais country;
}