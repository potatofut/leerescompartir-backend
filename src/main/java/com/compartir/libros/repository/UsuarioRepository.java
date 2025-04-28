package com.compartir.libros.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Búsqueda de libros por temática
    @Query("{ 'libros.tematicas': ?0 }")
    List<Usuario> findByLibrosTematicas(ObjectId tematicaId);
    
    // Búsqueda de libros por título (usando regex para búsqueda parcial)
    @Query("{ 'libros.titulo': { $regex: ?0, $options: 'i' } }")
    List<Usuario> findByLibrosTituloContainingIgnoreCase(String titulo);
    
    // Búsqueda de libros por autor (usando regex para búsqueda parcial)
    @Query("{ 'libros.autor': { $regex: ?0, $options: 'i' } }")
    List<Usuario> findByLibrosAutorContainingIgnoreCase(String autor);
    
    // Búsqueda combinada por temática y estado
    @Query("{ 'libros.tematicas': ?0, 'libros.estado': ?1 }")
    List<Usuario> findByLibrosTematicasAndEstado(ObjectId tematicaId, String estado);
    
    // Búsqueda por región (país)
    @Query("{ 'region.pais': ?0, 'libros.tematicas': ?1 }")
    List<Usuario> findByRegionPaisAndLibrosTematicas(String pais, ObjectId tematicaId);
    
    // Búsqueda por región (provincia)
    @Query("{ 'region.provincia': ?0, 'libros.tematicas': ?1 }")
    List<Usuario> findByRegionProvinciaAndLibrosTematicas(String provincia, ObjectId tematicaId);
    
    // Búsqueda por región (ciudad)
    @Query("{ 'region.ciudad': ?0, 'libros.tematicas': ?1 }")
    List<Usuario> findByRegionCiudadAndLibrosTematicas(String ciudad, ObjectId tematicaId);
    
    // Búsqueda combinada por región y estado
    @Query("{ 'region.pais': ?0, 'libros.tematicas': ?1, 'libros.estado': ?2 }")
    List<Usuario> findByRegionPaisAndLibrosTematicasAndEstado(String pais, ObjectId tematicaId, String estado);
}