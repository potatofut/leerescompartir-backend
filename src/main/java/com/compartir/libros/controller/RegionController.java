package com.compartir.libros.controller;

import com.compartir.libros.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regiones")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * Endpoint para obtener todos los nombres de continentes
     * @return Lista de nombres de continentes
     */
    @GetMapping("/continentes")
    public ResponseEntity<List<String>> obtenerTodosLosContinentes() {
        List<String> nombresContinentes = regionService.obtenerTodosLosContinentes();
        return ResponseEntity.ok(nombresContinentes);
    }

    /**
     * Endpoint para obtener todos los nombres de países de un continente específico
     * @param nombreContinente Nombre del continente
     * @return Lista de nombres de países
     */
    @GetMapping("/continentes/{nombreContinente}/paises")
    public ResponseEntity<List<String>> obtenerPaisesPorContinente(@PathVariable String nombreContinente) {
        List<String> nombresPaises = regionService.obtenerPaisesPorContinente(nombreContinente);
        return ResponseEntity.ok(nombresPaises);
    }

    /**
     * Endpoint para obtener todos los nombres de provincias de un país específico
     * @param nombreContinente Nombre del continente
     * @param nombrePais Nombre del país
     * @return Lista de nombres de provincias
     */
    @GetMapping("/continentes/{nombreContinente}/paises/{nombrePais}/provincias")
    public ResponseEntity<List<String>> obtenerProvinciasPorPais(
            @PathVariable String nombreContinente,
            @PathVariable String nombrePais) {
        List<String> nombresProvincias = regionService.obtenerProvinciasPorPais(nombreContinente, nombrePais);
        return ResponseEntity.ok(nombresProvincias);
    }

    /**
     * Endpoint para obtener todos los nombres de ciudades de una provincia específica
     * @param nombreContinente Nombre del continente
     * @param nombrePais Nombre del país
     * @param nombreProvincia Nombre de la provincia
     * @return Lista de nombres de ciudades
     */
    @GetMapping("/continentes/{nombreContinente}/paises/{nombrePais}/provincias/{nombreProvincia}/ciudades")
    public ResponseEntity<List<String>> obtenerCiudadesPorProvincia(
            @PathVariable String nombreContinente,
            @PathVariable String nombrePais,
            @PathVariable String nombreProvincia) {
        List<String> nombresCiudades = regionService.obtenerCiudadesPorProvincia(nombreContinente, nombrePais, nombreProvincia);
        return ResponseEntity.ok(nombresCiudades);
    }
}