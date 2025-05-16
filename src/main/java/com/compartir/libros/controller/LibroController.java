package com.compartir.libros.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.compartir.libros.dto.libro.CambioEstadoRequest;
import com.compartir.libros.dto.libro.LibroDTO;
import com.compartir.libros.dto.libro.LibroPrestamoDTO;
import com.compartir.libros.dto.libro.LibroRequestDTO;
import com.compartir.libros.dto.libro.LibroReservaRequestDTO;
import com.compartir.libros.dto.libro.LibroResponseDTO;
import com.compartir.libros.service.LibroService;

import jakarta.validation.Valid;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los libros.
 * Proporciona endpoints para crear, actualizar, eliminar y consultar libros.
 *
 * @author Sergio
 */
@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {
    private final LibroService libroService;

    /**
     * Obtiene la lista de libros del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @return Lista de libros del usuario
     */
    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> obtenerLibrosUsuario(Authentication authentication) {
        return ResponseEntity.ok(libroService.obtenerLibrosUsuario(authentication.getName()));
    }
    
    /**
     * Agrega un nuevo libro para el usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @param libroRequest Datos del libro a agregar
     * @return Libro agregado
     */
    @PostMapping
    public ResponseEntity<LibroResponseDTO> agregarLibro(Authentication authentication, @Valid @RequestBody LibroRequestDTO libroRequest) {
        return new ResponseEntity<>(libroService.agregarLibro(authentication.getName(), libroRequest), HttpStatus.CREATED);
    }

    /**
     * Actualiza un libro existente del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @param indice Índice del libro a actualizar
     * @param libroRequest Datos actualizados del libro
     * @return Libro actualizado
     */
    @PutMapping("/{indice}")
    public ResponseEntity<LibroResponseDTO> actualizarLibro(
            Authentication authentication,
            @PathVariable int indice,
            @Valid @RequestBody LibroRequestDTO libroRequest) {
        return ResponseEntity.ok(libroService.actualizarLibro(authentication.getName(), indice, libroRequest));
    }

    /**
     * Elimina un libro del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @param indice Índice del libro a eliminar
     * @return Respuesta vacía con código 204
     */
    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> eliminarLibro(Authentication authentication, @PathVariable int indice) {
        libroService.eliminarLibro(authentication.getName(), indice);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cambia el estado de un libro del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @param indice Índice del libro
     * @param request Datos del cambio de estado
     * @return Libro actualizado
     */
    @PutMapping("/{indice}/estado")
    public ResponseEntity<LibroResponseDTO> cambiarEstadoLibro(
            Authentication authentication,
            @PathVariable int indice,
            @Valid @RequestBody CambioEstadoRequest request) {
        return ResponseEntity.ok(libroService.cambiarEstadoLibro(authentication.getName(), indice, request));
    }

    /**
     * Filtra libros según diferentes criterios.
     *
     * @param tematicaId ID de la temática
     * @param estado Estado del libro
     * @param pais País
     * @param provincia Provincia
     * @param ciudad Ciudad
     * @return Lista de libros filtrados
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<LibroDTO>> filtrarLibros(
            @RequestParam(required = false) String tematicaId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String ciudad) {
        return ResponseEntity.ok(libroService.filtrarLibros(tematicaId, estado, pais, provincia, ciudad));
    }

    /**
     * Busca libros por título.
     *
     * @param query Término de búsqueda
     * @return Lista de libros encontrados
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscarLibrosPorTitulo(@RequestParam String query) {
        return ResponseEntity.ok(libroService.buscarLibros(query));
    }

    /**
     * Obtiene la lista de libros prestados del usuario autenticado.
     *
     * @param authentication Información de autenticación del usuario
     * @return Lista de libros prestados
     */
    @GetMapping("/prestados")
    public ResponseEntity<List<LibroPrestamoDTO>> obtenerLibrosPrestados(Authentication authentication) {
        return ResponseEntity.ok(libroService.obtenerLibrosPrestados(authentication.getName()));
    }

    /**
     * Registra la devolución de un libro.
     *
     * @param authentication Información de autenticación del usuario
     * @param libroPrestamoRequest Datos de la devolución
     * @return Respuesta vacía con código 204
     */
    @PostMapping("/devolver")
    public ResponseEntity<Void> devolverLibro(Authentication authentication, @Valid @RequestBody LibroReservaRequestDTO libroPrestamoRequest) {
        libroService.devolverLibro(authentication.getName(), libroPrestamoRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reserva un libro.
     *
     * @param authentication Información de autenticación del usuario
     * @param reserva Datos de la reserva
     * @return Respuesta vacía con código 204
     */
    @PostMapping("/reservar")
    public ResponseEntity<Void> reservarLibro(Authentication authentication, @Valid @RequestBody LibroReservaRequestDTO reserva) {
        libroService.reservarLibro(authentication.getName(), reserva);
        return ResponseEntity.noContent().build();
    }
}