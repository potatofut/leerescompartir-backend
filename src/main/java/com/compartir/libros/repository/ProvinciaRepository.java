package com.compartir.libros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Provincia;

@Repository
public interface ProvinciaRepository extends MongoRepository<Provincia, Integer> {
    List<Provincia> findByCountryIdAndTypeEquals(Integer countryId, String type);
    Optional<Provincia> findByNameAndCountryIdAndTypeEquals(String name, Integer countryId, String type);
}
