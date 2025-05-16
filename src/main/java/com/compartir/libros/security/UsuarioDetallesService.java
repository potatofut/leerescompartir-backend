package com.compartir.libros.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.compartir.libros.model.Usuario;
import com.compartir.libros.repository.UsuarioRepository;

import java.util.Collections;

/**
 * Servicio personalizado para cargar los detalles del usuario durante la autenticación.
 * Implementa UserDetailsService de Spring Security para integrar la autenticación
 * con el modelo de usuario de la aplicación.
 *
 * @author Sergio
 */
@Service
@RequiredArgsConstructor
public class UsuarioDetallesService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles del usuario por su email.
     * Convierte el modelo Usuario de la aplicación en un UserDetails de Spring Security.
     *
     * @param email Email del usuario a cargar
     * @return Detalles del usuario para Spring Security
     * @throws UsernameNotFoundException si no se encuentra el usuario
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new UsuarioDetalles(usuario, Collections.emptyList());
    }
}