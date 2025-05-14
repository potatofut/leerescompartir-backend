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
 * Servicio encargado de gestionar las operaciones relacionadas con las temáticas de libros.
 * Proporciona métodos para obtener todas las temáticas o una temática específica por su identificador.
 * 
 * <p>Este servicio actúa como intermediario entre el controlador y el repositorio de temáticas.</p>
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
     * @return una lista de {@link TematicaDTO} que representa todas las temáticas almacenadas.
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
     * Obtiene una temática específica según su identificador.
     *
     * @param id el identificador único de la temática en formato {@code String}.
     * @return una instancia de {@link TematicaDTO} con los datos de la temática correspondiente.
     * @throws RuntimeException si no se encuentra una temática con el identificador proporcionado.
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