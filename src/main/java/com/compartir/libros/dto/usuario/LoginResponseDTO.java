package com.compartir.libros.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String id;
    private String nombre;
    private String email;
    private String imagen;
    private String cp;
    private String telefono;
    private String biografia;
    private String intereses;
    private String ciudad;
    private String provincia;
    private String pais;
    private String continente;
}
