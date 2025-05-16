package com.compartir.libros.service;

import com.compartir.libros.dto.tematica.TematicaDTO;
import com.compartir.libros.model.Tematica;
import com.compartir.libros.repository.TematicaRepository;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona las operaciones relacionadas con las temáticas de libros.
 * Proporciona funcionalidades para obtener y consultar las diferentes temáticas
 * disponibles en el sistema.
 *
 * @author Sergio
 */
@Service
@RequiredArgsConstructor
public class TematicaService {

    /** Repositorio para el acceso a los datos de las temáticas. */
    private final TematicaRepository tematicaRepository;

    /**
     * Obtiene todas las temáticas disponibles en el sistema.
     *
     * @return Lista de temáticas en formato DTO
     */
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

    /**
     * Obtiene una temática específica por su identificador.
     *
     * @param id Identificador de la temática
     * @return Temática encontrada en formato DTO
     * @throws RuntimeException si no se encuentra la temática
     */
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