package com.compartir.libros.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Region;

import java.util.Optional;

@Repository
public interface RegionRepository extends MongoRepository<Region, Integer> {
    Optional<Region> findByName(String name);
}