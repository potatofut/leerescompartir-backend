package com.compartir.libros.service;

import com.compartir.libros.model.Region;
import com.compartir.libros.model.Ciudad;
import com.compartir.libros.model.Pais;
import com.compartir.libros.model.Provincia;
import com.compartir.libros.repository.CiudadRepository;
import com.compartir.libros.repository.PaisRepository;
import com.compartir.libros.repository.ProvinciaRepository;
import com.compartir.libros.repository.RegionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final PaisRepository countryRepository;
    private final ProvinciaRepository stateRepository;
    private final CiudadRepository cityRepository;

    private static final String PROVINCE_TYPE = "province";

    public RegionService(RegionRepository regionRepository, 
                        PaisRepository countryRepository,
                        ProvinciaRepository stateRepository, 
                        CiudadRepository cityRepository) {
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * Obtiene todos los nombres de continentes (regiones)
     * @return Lista de nombres de las regiones
     */
    public List<String> obtenerTodosLosContinentes() {
        return regionRepository.findAll().stream()
                .map(Region::getName)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los nombres de países de un continente específico
     * @param nombreContinente Nombre del continente
     * @return Lista de nombres de países del continente
     */
    public List<String> obtenerPaisesPorContinente(String nombreContinente) {
        // Buscar el continente por nombre
        Region region = regionRepository.findByName(nombreContinente)
                .orElseThrow(() -> new IllegalArgumentException("Continente no encontrado: " + nombreContinente));
        
        // Buscar países por ID de región
        return countryRepository.findByRegionId(region.getId()).stream()
                .map(Pais::getName)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los nombres de provincias de un país específico
     * @param nombreContinente Nombre del continente
     * @param nombrePais Nombre del país
     * @return Lista de nombres de provincias del país
     */
    public List<String> obtenerProvinciasPorPais(String nombreContinente, String nombrePais) {
        // Buscar el continente por nombre
        Region region = regionRepository.findByName(nombreContinente)
                .orElseThrow(() -> new IllegalArgumentException("Continente no encontrado: " + nombreContinente));
        
        // Buscar el país por nombre
        Pais country = countryRepository.findByName(nombrePais)
                .orElseThrow(() -> new IllegalArgumentException("País no encontrado: " + nombrePais));
        
        // Verificar que el país pertenezca al continente especificado
        if (!country.getRegionId().equals(region.getId())) {
            throw new IllegalArgumentException("El país " + nombrePais + 
                                              " no pertenece al continente " + nombreContinente);
        }
        
        // Buscar provincias por ID de país
        return stateRepository.findByCountryIdAndTypeEquals(country.getId(), PROVINCE_TYPE).stream()
                .map(Provincia::getName)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los nombres de ciudades de una provincia específica
     * @param nombreContinente Nombre del continente
     * @param nombrePais Nombre del país
     * @param nombreProvincia Nombre de la provincia
     * @return Lista de nombres de ciudades de la provincia
     */
    public List<String> obtenerCiudadesPorProvincia(String nombreContinente, String nombrePais, String nombreProvincia) {
        // Buscar el continente por nombre
        Region region = regionRepository.findByName(nombreContinente)
                .orElseThrow(() -> new IllegalArgumentException("Continente no encontrado: " + nombreContinente));
        
        // Buscar el país por nombre
        Pais country = countryRepository.findByName(nombrePais)
                .orElseThrow(() -> new IllegalArgumentException("País no encontrado: " + nombrePais));
        
        // Verificar que el país pertenezca al continente especificado
        if (!country.getRegionId().equals(region.getId())) {
            throw new IllegalArgumentException("El país " + nombrePais + 
                                              " no pertenece al continente " + nombreContinente);
        }
        
        // Buscar la provincia por nombre y ID de país
        Provincia state = stateRepository.findByNameAndCountryIdAndTypeEquals(nombreProvincia, country.getId(), PROVINCE_TYPE)
                .orElseThrow(() -> new IllegalArgumentException("Provincia no encontrada: " + nombreProvincia + 
                                                              " en país " + nombrePais));
        
        // Buscar ciudades por ID de provincia
        return cityRepository.findByStateId(state.getId()).stream()
                .map(Ciudad::getName)
                .collect(Collectors.toList());
    }
}