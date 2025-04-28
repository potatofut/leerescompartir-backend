package com.compartir.libros.service;

import com.compartir.libros.dto.tematica.TematicaDTO;
import com.compartir.libros.model.Tematica;
import com.compartir.libros.repository.TematicaRepository;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TematicaService {
    private final TematicaRepository tematicaRepository;

    public List<TematicaDTO> obtenerTodasTematicas() {
        return tematicaRepository.findAll().stream()
            .map(tematica -> new TematicaDTO(
                tematica.getId().toHexString(),
                tematica.getNombre(),
                tematica.getImagen(),
                tematica.getDescripcion()
            ))
            .collect(Collectors.toList());
    }

    public TematicaDTO obtenerTematicaPorId(String id) {
        Tematica tematica = tematicaRepository.findById(new ObjectId(id))
            .orElseThrow(() -> new RuntimeException("Temática no encontrada"));
        
        return new TematicaDTO(
            tematica.getId().toHexString(),
            tematica.getNombre(),
            tematica.getImagen(),
            tematica.getDescripcion()
        );
    }
}