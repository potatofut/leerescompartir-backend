package com.compartir.libros.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo que representa una ciudad en la base de datos.
 * Incluye información geográfica y referencias a su provincia y país.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cities")
public class Ciudad {
    @Id
    private Integer id;
    private String name;
    
    @Field("state_id")
    private Integer stateId;
    
    @Field("state_code")
    private String stateCode;
    
    @Field("state_name")
    private String stateName;
    
    @Field("country_id")
    private Integer countryId;
    
    @Field("country_code")
    private String countryCode;
    
    @Field("country_name")
    private String countryName;
    
    private String latitude;
    private String longitude;
    private String wikiDataId;
    
    @DBRef
    private Provincia state;
    
    @DBRef
    private Pais country;
    
    private Location location;

    /**
     * Clase interna que representa la ubicación geográfica de la ciudad.
     * Utiliza coordenadas para almacenar la posición exacta.
     */
    public static class Location {
        private String type;
        private List<Double> coordinates;

        // Constructors
        public Location() {
        }

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }
    }
}
