package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo que representa un usuario en la base de datos.
 * Incluye información personal, ubicación y lista de libros del usuario.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String email;
    private String password;
    private String imagen;
    private String cp;
    private String telefono;
    private String biografia;
    private String intereses;
    private RegionUsuario region;
    
    private String verificationToken;
    private LocalDateTime tokenGeneratedAt;
    private LocalDateTime tokenVerifiedAt;
    private Boolean isVerified = false;

    private List<Libro> libros = new ArrayList<>();
}
