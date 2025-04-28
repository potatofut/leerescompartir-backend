package com.compartir.libros.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Tematica;

import java.util.List;

@Repository
public interface TematicaRepository extends MongoRepository<Tematica, ObjectId> {
    List<Tematica> findByNombreIn(List<String> nombres);
}
