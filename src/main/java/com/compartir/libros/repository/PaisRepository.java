package com.compartir.libros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Pais;

@Repository
public interface PaisRepository extends MongoRepository<Pais, Integer> {
    List<Pais> findByRegionId(Integer regionId);
    Optional<Pais> findByName(String name);
}

