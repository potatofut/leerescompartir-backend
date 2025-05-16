package com.compartir.libros.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para la solicitud de cambio de contraseña de un usuario.
 * Incluye validaciones para asegurar la seguridad de la contraseña.
 *
 * @author Sergio
 */
@Data
public class CambioPasswordRequestDTO {
    @NotBlank(message = "La contraseña actual no puede estar vacía")
    private String passwordActual;
    
    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres")
    private String nuevaPassword;
}
