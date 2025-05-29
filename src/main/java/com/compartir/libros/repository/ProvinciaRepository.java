package com.compartir.libros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Provincia;

/**
 * Repositorio para la gestión de provincias en la base de datos MongoDB.
 * Proporciona métodos para consultar provincias por país y tipo.
 * 
 * Métodos disponibles:
 * - findByCountryIdAndTypeEquals: Busca provincias por ID de país y tipo específico
 * - findByNameAndCountryIdAndTypeEquals: Busca una provincia específica por nombre, país y tipo
 *
 * @author Sergio
 */
@Repository
public interface ProvinciaRepository extends MongoRepository<Provincia, Integer> {
    List<Provincia> findByCountryId(Integer countryId);
    Optional<Provincia> findByNameAndCountryId(String name, Integer countryId);
}
