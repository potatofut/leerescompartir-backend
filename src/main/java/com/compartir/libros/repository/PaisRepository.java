package com.compartir.libros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Pais;

/**
 * Repositorio para la gestión de países en la base de datos MongoDB.
 * Proporciona métodos para consultar países por región y nombre.
 * 
 * Métodos disponibles:
 * - findByRegionId: Busca todos los países que pertenecen a una región específica
 * - findByName: Busca un país por su nombre exacto
 *
 * @author Sergio
 */
@Repository
public interface PaisRepository extends MongoRepository<Pais, Integer> {
    List<Pais> findByRegionId(Integer regionId);
    Optional<Pais> findByName(String name);
}

