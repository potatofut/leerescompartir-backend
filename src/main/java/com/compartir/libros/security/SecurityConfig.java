package com.compartir.libros.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

/**
 * Configuración de seguridad de la aplicación.
 * Define las reglas de autenticación, autorización y CORS.
 * 
 * Características principales:
 * - Configuración de endpoints públicos y protegidos
 * - Configuración de CORS para permitir peticiones desde el frontend
 * - Configuración del codificador de contraseñas
 * - Configuración del gestor de autenticación
 *
 * @author Sergio
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioDetallesService customUserDetailsService;

    /**
     * Configura la cadena de filtros de seguridad.
     * Define los endpoints públicos y protegidos, y configura CORS.
     *
     * @param http Configuración de seguridad HTTP
     * @return Cadena de filtros de seguridad configurada
     * @throws Exception si hay un error en la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/favicon.ico").permitAll()

                .requestMatchers("/api/libros/tematica/**", "/api/libros/buscar/**", "/api/libros/filtrar/**", "/api/tematicas/**").permitAll()
            
                .requestMatchers("/api/usuarios/login", "/api/usuarios/registro", "/api/usuarios/verificar").permitAll()
            
                .requestMatchers("/api/libros/**", "/api/usuarios/**").authenticated()

                .requestMatchers("/api/regiones/**").permitAll()
            
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> httpBasic.realmName("MyAppRealm"));
        
        return http.build();
    }

    /**
     * Crea un codificador de contraseñas para el hash seguro de contraseñas.
     *
     * @return Instancia de BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el gestor de autenticación con el servicio de detalles de usuario personalizado
     * y el codificador de contraseñas.
     *
     * @param http Configuración de seguridad HTTP
     * @param passwordEncoder Codificador de contraseñas a utilizar
     * @return Gestor de autenticación configurado
     * @throws Exception si hay un error durante la configuración
     */
    @Bean
    AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
        
        return authenticationManagerBuilder.build();
    }

    /**
     * Configura el origen de configuración CORS para permitir peticiones desde el frontend.
     * Permite métodos HTTP específicos y headers necesarios.
     *
     * @return Fuente de configuración CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000","https://leerescompartir.com", "https://www.leerescompartir.com"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
