package com.compartir.libros.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compartir.libros.dto.tematica.TematicaDTO;
import com.compartir.libros.service.TematicaService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestionar las operaciones relacionadas con las temáticas de los libros.
 * Proporciona endpoints para consultar las temáticas disponibles.
 *
 * @author Sergio
 */
@RestController
@RequestMapping("/api/tematicas")
@RequiredArgsConstructor
public class TematicaController {
    private final TematicaService tematicaService;

    /**
     * Obtiene la lista de todas las temáticas disponibles.
     *
     * @return Lista de temáticas
     */
    @GetMapping
    public ResponseEntity<List<TematicaDTO>> obtenerTodasTematicas() {
        return ResponseEntity.ok(tematicaService.obtenerTodasTematicas());
    }

    /**
     * Obtiene una temática específica por su identificador.
     *
     * @param id Identificador de la temática
     * @return Temática encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<TematicaDTO> obtenerTematicaPorId(@PathVariable String id) {
        return ResponseEntity.ok(tematicaService.obtenerTematicaPorId(id));
    }
}
