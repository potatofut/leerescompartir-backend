package com.compartir.libros.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Ciudad;

/**
 * Repositorio para la gestión de ciudades en la base de datos MongoDB.
 * Proporciona métodos para consultar ciudades por provincia.
 * 
 * Métodos disponibles:
 * - findByStateId: Busca todas las ciudades que pertenecen a una provincia específica
 *
 * @author Sergio
 */
@Repository
public interface CiudadRepository extends MongoRepository<Ciudad, Integer> {
    List<Ciudad> findByStateId(Integer stateId);
}