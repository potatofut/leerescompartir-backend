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

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {
    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> obtenerLibrosUsuario(Authentication authentication) {
        return ResponseEntity.ok(libroService.obtenerLibrosUsuario(authentication.getName()));
    }
    
    @PostMapping
    public ResponseEntity<LibroResponseDTO> agregarLibro(Authentication authentication, @Valid @RequestBody LibroRequestDTO libroRequest) {
        return new ResponseEntity<>(libroService.agregarLibro(authentication.getName(), libroRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{indice}")
    public ResponseEntity<LibroResponseDTO> actualizarLibro(
            Authentication authentication,
            @PathVariable int indice,
            @Valid @RequestBody LibroRequestDTO libroRequest) {
        return ResponseEntity.ok(libroService.actualizarLibro(authentication.getName(), indice, libroRequest));
    }

    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> eliminarLibro(Authentication authentication, @PathVariable int indice) {
        libroService.eliminarLibro(authentication.getName(), indice);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{indice}/estado")
    public ResponseEntity<LibroResponseDTO> cambiarEstadoLibro(
            Authentication authentication,
            @PathVariable int indice,
            @Valid @RequestBody CambioEstadoRequest request) {
        return ResponseEntity.ok(libroService.cambiarEstadoLibro(authentication.getName(), indice, request));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<LibroDTO>> filtrarLibros(
            @RequestParam(required = false) String tematicaId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String ciudad) {
        return ResponseEntity.ok(libroService.filtrarLibros(tematicaId, estado, pais, provincia, ciudad));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscarLibrosPorTitulo(@RequestParam String query) {
        return ResponseEntity.ok(libroService.buscarLibros(query));
    }

    @GetMapping("/prestados")
    public ResponseEntity<List<LibroPrestamoDTO>> obtenerLibrosPrestados(Authentication authentication) {
        return ResponseEntity.ok(libroService.obtenerLibrosPrestados(authentication.getName()));
    }

    @PostMapping("/devolver")
    public ResponseEntity<Void> devolverLibro(Authentication authentication, @Valid @RequestBody LibroReservaRequestDTO libroPrestamoRequest) {
        libroService.devolverLibro(authentication.getName(), libroPrestamoRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reservar")
    public ResponseEntity<Void> reservarLibro(Authentication authentication, @Valid @RequestBody LibroReservaRequestDTO reserva) {
        libroService.reservarLibro(authentication.getName(), reserva);
        return ResponseEntity.noContent().build();
    }
}