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

@RestController
@RequestMapping("/api/tematicas")
@RequiredArgsConstructor
public class TematicaController {
    private final TematicaService tematicaService;

    @GetMapping
    public ResponseEntity<List<TematicaDTO>> obtenerTodasTematicas() {
        return ResponseEntity.ok(tematicaService.obtenerTodasTematicas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TematicaDTO> obtenerTematicaPorId(@PathVariable String id) {
        return ResponseEntity.ok(tematicaService.obtenerTematicaPorId(id));
    }
}
