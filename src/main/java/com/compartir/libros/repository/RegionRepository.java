package com.compartir.libros.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Region;

import java.util.Optional;

/**
 * Repositorio para la gestión de regiones en la base de datos MongoDB.
 * Proporciona métodos para consultar regiones por nombre.
 * 
 * Métodos disponibles:
 * - findByName: Busca una región por su nombre exacto
 *
 * @author Sergio
 */
@Repository
public interface RegionRepository extends MongoRepository<Region, Integer> {
    Optional<Region> findByName(String name);
}