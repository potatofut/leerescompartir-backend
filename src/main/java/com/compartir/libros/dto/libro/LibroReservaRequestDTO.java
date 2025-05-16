package com.compartir.libros.dto.libro;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la solicitud de reserva de un libro.
 * Incluye validaciones para el email y el título del libro.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroReservaRequestDTO {
    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    private String emailUsuario;
}
