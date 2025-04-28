package com.compartir.libros.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Ciudad;

@Repository
public interface CiudadRepository extends MongoRepository<Ciudad, Integer> {
    List<Ciudad> findByStateId(Integer stateId);
}