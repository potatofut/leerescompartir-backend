package com.compartir.libros.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.compartir.libros.model.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de usuarios en la base de datos MongoDB.
 * Proporciona métodos para consultar usuarios y sus libros por diferentes
 * criterios.
 * 
 * Métodos disponibles:
 * - findByEmail: Busca un usuario por su email
 * - existsByEmail: Verifica si existe un usuario con un email específico
 * - findByLibrosTematicas: Busca usuarios que tienen libros de una temática
 * específica
 * - findByLibrosTituloOrAutorContainingIgnoreCase: Busca usuarios que tienen
 * libros por título o autor
 * - findByLibrosTematicasAndEstado: Busca usuarios que tienen libros de una
 * temática y estado específicos
 * - findByRegionPaisAndLibrosTematicas: Busca usuarios por país y temática de
 * libros
 * - findByRegionProvinciaAndLibrosTematicas: Busca usuarios por provincia y
 * temática de libros
 * - findByRegionCiudadAndLibrosTematicas: Busca usuarios por ciudad y temática
 * de libros
 * - findByRegionPaisAndLibrosTematicasAndEstado: Busca usuarios por país,
 * temática y estado de libros
 * - findByLibrosEstado: Busca usuarios que tienen libros en un estado
 * específico
 * - findByRegionPais: Busca usuarios por país
 * - findByRegionProvincia: Busca usuarios por provincia
 * - findByRegionCiudad: Busca usuarios por ciudad
 * - findByRegionPaisAndLibrosEstado: Busca usuarios por país y estado de libros
 *
 * @author Sergio
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
  Optional<Usuario> findByEmail(String email);

  boolean existsByEmail(String email);

  // Búsqueda de libros por temática
  @Query("{ 'libros.tematicas': ?0 }")
  List<Usuario> findByLibrosTematicas(ObjectId tematicaId);

  // Búsqueda de libros por título o autor (usando regex para búsqueda parcial)
  @Query("{ $or: [ {'libros.titulo': { $regex: ?0, $options: 'i' }}, {'libros.autor': { $regex: ?0, $options: 'i' }} ] }")
  List<Usuario> findByLibrosTituloOrAutorContainingIgnoreCase(String searchString);

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

  // Búsqueda por estado del libro
  @Query("{ 'libros.estado': ?0 }")
  List<Usuario> findByLibrosEstado(String estado);

  // Búsqueda por país
  @Query("{ 'region.pais': ?0 }")
  List<Usuario> findByRegionPais(String pais);

  // Búsqueda por provincia
  @Query("{ 'region.provincia': ?0 }")
  List<Usuario> findByRegionProvincia(String provincia);

  // Búsqueda por ciudad
  @Query("{ 'region.ciudad': ?0 }")
  List<Usuario> findByRegionCiudad(String ciudad);

  // Búsqueda combinada por país y estado del libro
  @Query("{ 'region.pais': ?0, 'libros.estado': ?1 }")
  List<Usuario> findByRegionPaisAndLibrosEstado(String pais, String estado);

  // Por región (pais-provincia-ciudad), temática y estado
  @Query("{ 'region.pais': ?0, 'region.provincia': ?1, 'region.ciudad': ?2, 'libros.tematicas': ?3, 'libros.estado': ?4 }")
  List<Usuario> findByRegionAndLibrosTematicasAndEstado(String pais, String provincia, String ciudad,
      ObjectId tematicaId, String estado);

  // Por región (pais-provincia), temática y estado
  @Query("{ 'region.pais': ?0, 'region.provincia': ?1, 'libros.tematicas': ?2, 'libros.estado': ?3 }")
  List<Usuario> findByRegionAndLibrosTematicasAndEstado(String pais, String provincia, ObjectId tematicaId,
      String estado);

  // Por región (pais), temática y estado
  @Query("{ 'region.pais': ?0, 'libros.tematicas': ?1, 'libros.estado': ?2 }")
  List<Usuario> findByRegionAndLibrosTematicasAndEstado(String pais, ObjectId tematicaId, String estado);

  // Y las variantes equivalentes sin temática
  @Query("{ 'region.pais': ?0, 'region.provincia': ?1, 'region.ciudad': ?2, 'libros.estado': ?3 }")
  List<Usuario> findByRegionAndLibrosEstado(String pais, String provincia, String ciudad, String estado);

  @Query("{ 'region.pais': ?0, 'region.provincia': ?1, 'libros.estado': ?2 }")
  List<Usuario> findByRegionAndLibrosEstado(String pais, String provincia, String estado);

  @Query("{ 'region.pais': ?0, 'libros.estado': ?1 }")
  List<Usuario> findByRegionAndLibrosEstado(String pais, String estado);

  Optional<Usuario> findByVerificationToken(String token);
}