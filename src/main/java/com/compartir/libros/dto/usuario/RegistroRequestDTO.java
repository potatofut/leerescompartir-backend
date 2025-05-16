package com.compartir.libros.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para la solicitud de registro de un nuevo usuario.
 * Incluye validaciones para todos los campos obligatorios y el formato del email.
 *
 * @author Sergio
 */
@Data
public class RegistroRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    private String email;
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudad;
    
    @NotBlank(message = "La provincia no puede estar vacía")
    private String provincia;
    
    @NotBlank(message = "El país no puede estar vacío")
    private String pais;
    
    @NotBlank(message = "El continente no puede estar vacío")
    private String continente;
}
