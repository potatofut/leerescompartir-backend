package com.compartir.libros.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.compartir.libros.model.Usuario;

/**
 * Implementación personalizada de User de Spring Security.
 * Extiende la funcionalidad básica de User para incluir el nombre del usuario.
 *
 * @author Sergio
 */
public class UsuarioDetalles extends User {

    private final String nombre;

    /**
     * Constructor que inicializa los detalles del usuario.
     *
     * @param usuario Usuario de la aplicación
     * @param authorities Colección de autoridades del usuario
     */
    public UsuarioDetalles(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getPassword(), authorities);
        this.nombre = usuario.getNombre();
    }
    
    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }
    
}
