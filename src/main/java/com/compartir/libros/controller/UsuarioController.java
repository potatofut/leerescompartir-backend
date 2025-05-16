package com.compartir.libros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compartir.libros.dto.usuario.CambioPasswordRequestDTO;
import com.compartir.libros.dto.usuario.LoginRequestDTO;
import com.compartir.libros.dto.usuario.LoginResponseDTO;
import com.compartir.libros.dto.usuario.RegistroRequestDTO;
import com.compartir.libros.dto.usuario.UsuarioUpdateRequestDTO;
import com.compartir.libros.model.Usuario;
import com.compartir.libros.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los usuarios.
 * Proporciona endpoints para registro, login, actualización de perfil y gestión de contraseñas.
 *
 * @author Sergio
 */
@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    /**
     * Autentica a un usuario en el sistema.
     *
     * @param loginRequest Datos de inicio de sesión
     * @return Respuesta con el token de autenticación
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.debug("Recibida solicitud de login para: {}", loginRequest.getEmail());
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param registroRequest Datos de registro del usuario
     * @return Usuario registrado
     */
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@Valid @RequestBody RegistroRequestDTO registroRequest) {
        log.debug("Recibida solicitud de registro para: {}", registroRequest.getEmail());
        return new ResponseEntity<>(usuarioService.registro(registroRequest), HttpStatus.CREATED);
    }

    /**
     * Cambia la contraseña del usuario autenticado.
     *
     * @param auth Información de autenticación del usuario
     * @param request Datos del cambio de contraseña
     * @return Respuesta vacía con código 200
     */
    @PostMapping("/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(
            Authentication auth, 
            @Valid @RequestBody CambioPasswordRequestDTO request) {

        log.debug("Recibida solicitud de cambio de contraseña para: {}", auth.getName());
        usuarioService.cambiarPassword(auth.getName(), request);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtiene el perfil del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @return Perfil del usuario
     */
    @GetMapping("/perfil")
    public ResponseEntity<Usuario> obtenerPerfil(Authentication authentication) {
        log.debug("Recuperando perfil para: {}", authentication.getName());
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorEmail(authentication.getName()));
    }

    /**
     * Actualiza el perfil del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @param request Datos actualizados del usuario
     * @return Usuario actualizado
     */
    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(Authentication authentication, @Valid @RequestBody UsuarioUpdateRequestDTO request) {
        log.debug("Actualizando perfil para: {}", authentication.getName());
        return ResponseEntity.ok(usuarioService.actualizarUsuario(authentication.getName(), request));
    }
}
