package com.compartir.libros.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Tematica;

import java.util.List;

/**
 * Repositorio para la gestión de temáticas en la base de datos MongoDB.
 * Proporciona métodos para consultar temáticas por nombre.
 * 
 * Métodos disponibles:
 * - findByNombreIn: Busca temáticas por una lista de nombres
 *
 * @author Sergio
 */
@Repository
public interface TematicaRepository extends MongoRepository<Tematica, ObjectId> {
    List<Tematica> findByNombreIn(List<String> nombres);
}
