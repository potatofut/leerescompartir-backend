package com.compartir.libros.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para la solicitud de actualización de datos de un usuario.
 * Incluye validaciones para los campos obligatorios y el formato del email.
 *
 * @author Sergio
 */
@Data
public class UsuarioUpdateRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
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
