package com.compartir.libros.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.compartir.libros.dto.usuario.CambioPasswordRequestDTO;
import com.compartir.libros.dto.usuario.LoginRequestDTO;
import com.compartir.libros.dto.usuario.LoginResponseDTO;
import com.compartir.libros.dto.usuario.RegistroRequestDTO;
import com.compartir.libros.dto.usuario.UsuarioUpdateRequestDTO;
import com.compartir.libros.model.RegionUsuario;
import com.compartir.libros.model.Usuario;
import com.compartir.libros.repository.UsuarioRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que gestiona todas las operaciones relacionadas con los usuarios.
 * Incluye funcionalidades para autenticación, registro, actualización de datos
 * y gestión de contraseñas.
 *
 * @author Sergio
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GmailAPIService gmailAPIService;

    /**
     * Autentica a un usuario y devuelve sus datos.
     *
     * @param loginRequest Datos de inicio de sesión
     * @return Datos del usuario autenticado
     * @throws RuntimeException si el usuario no existe o la contraseña es incorrecta
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        if (!usuario.getIsVerified()) {
            throw new RuntimeException("Por favor verifica tu email antes de iniciar sesión");
        }

        return new LoginResponseDTO(
            usuario.getId(), 
            usuario.getNombre(), 
            usuario.getEmail(),
            usuario.getImagen(),
            usuario.getCp(),
            usuario.getTelefono(),
            usuario.getBiografia(),
            usuario.getIntereses(),
            usuario.getRegion().getCiudad(),
            usuario.getRegion().getProvincia(),
            usuario.getRegion().getPais(),
            usuario.getRegion().getContinente()
        );
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param registroRequest Datos del nuevo usuario
     * @return Usuario registrado
     * @throws RuntimeException si el email ya está registrado
     */
    public Usuario registro(RegistroRequestDTO registroRequest) {
        if (usuarioRepository.existsByEmail(registroRequest.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        RegionUsuario region = new RegionUsuario(
            registroRequest.getCiudad(),
            registroRequest.getProvincia(),
            registroRequest.getPais(),
            registroRequest.getContinente()
        );

        Usuario usuario = new Usuario();
        usuario.setNombre(registroRequest.getNombre());
        usuario.setEmail(registroRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));
        usuario.setRegion(region);
        usuario.setLibros(new ArrayList<>());

        // Generate verification token
        generateVerificationToken(usuario);
        
        // Save user first to get the ID
        usuario = usuarioRepository.save(usuario);

        // Send verification email
        try {
            String verificationUrl = "http://localhost:3000/verificacion?token=" + usuario.getVerificationToken();
            String emailBody = "Por favor verifica tu email haciendo clic en el siguiente enlace: <a href=\"" + verificationUrl + "\">Verificar Email</a>";
            
            gmailAPIService.sendMessage(
                usuario.getEmail(),
                "Verifica tu cuenta en Leer es Compartir",
                emailBody,
                null
            );
        } catch (Exception e) {
            log.error("Error al enviar el email de verificación", e);
        }

        return usuario;
    }

    private void generateVerificationToken(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        usuario.setVerificationToken(token);
        usuario.setTokenGeneratedAt(LocalDateTime.now());
        usuario.setIsVerified(false);
    }

    public boolean verifyUser(String token) {
        Usuario usuario = usuarioRepository.findByVerificationToken(token)
            .orElseThrow(() -> new RuntimeException("Token de verificación no válido"));
        
        // Check if token is expired (24 hours validity)
        if (usuario.getTokenGeneratedAt().plusHours(24).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token de verificación expirado");
        }
        
        usuario.setIsVerified(true);
        usuario.setTokenVerifiedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
        return true;
    }

    /**
     * Cambia la contraseña de un usuario.
     *
     * @param email Email del usuario
     * @param request Datos para el cambio de contraseña
     * @throws RuntimeException si el usuario no existe o la contraseña actual es incorrecta
     */
    public void cambiarPassword(String email, CambioPasswordRequestDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(request.getNuevaPassword()));
        usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param email Email del usuario a actualizar
     * @param request Nuevos datos del usuario
     * @return Usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
    public Usuario actualizarUsuario(String email, UsuarioUpdateRequestDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());

        if (request.getImagen() != null) {
            // Verificar si la imagen es válida (por ejemplo, comienza con "data:")
            if (request.getImagen().startsWith("data:")) {
                log.debug("Actualizando imagen de usuario");
                usuario.setImagen(request.getImagen());
            } else {
                log.warn("Formato de imagen no válido: no comienza con 'data:'");
                // No actualizamos la imagen si no tiene formato válido
            }
        }

        usuario.setCp(request.getCp());
        usuario.setTelefono(request.getTelefono());
        usuario.setBiografia(request.getBiografia());
        usuario.setIntereses(request.getIntereses());

        RegionUsuario region = new RegionUsuario(
            request.getCiudad(),
            request.getProvincia(),
            request.getPais(),
            request.getContinente()
        );
        usuario.setRegion(region);

        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email Email del usuario
     * @return Usuario encontrado
     * @throws RuntimeException si el usuario no existe
     */
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}