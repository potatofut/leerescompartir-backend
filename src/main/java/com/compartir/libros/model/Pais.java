package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "countries")
public class Pais {
    @Id
    private Integer id;
    private String name;
    private String iso3;
    private String iso2;
    private String numeric_code;
    private String phonecode;
    private String capital;
    private String currency;
    private String currency_name;
    private String currency_symbol;
    private String tld;
    private String native_name;
    
    @DBRef
    private Region region;
    
    @Field("region_id")
    private Integer regionId;
    
    @DBRef
    private Subregion subregion;
    
    @Field("subregion_id")
    private Integer subregionId;
    
    private String nationality;
    private List<Timezone> timezones;
    private Map<String, String> translations;
    private String latitude;
    private String longitude;
    private String emoji;
    private String emojiU;

    // Inner class for timezone
    public static class Timezone {
        private String zoneName;
        private Integer gmtOffset;
        private String gmtOffsetName;
        private String abbreviation;
        private String tzName;

        // Constructors
        public Timezone() {
        }

        // Getters and Setters
        public String getZoneName() {
            return zoneName;
        }

        public void setZoneName(String zoneName) {
            this.zoneName = zoneName;
        }

        public Integer getGmtOffset() {
            return gmtOffset;
        }

        public void setGmtOffset(Integer gmtOffset) {
            this.gmtOffset = gmtOffset;
        }

        public String getGmtOffsetName() {
            return gmtOffsetName;
        }

        public void setGmtOffsetName(String gmtOffsetName) {
            this.gmtOffsetName = gmtOffsetName;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getTzName() {
            return tzName;
        }

        public void setTzName(String tzName) {
            this.tzName = tzName;
        }
    }
}
