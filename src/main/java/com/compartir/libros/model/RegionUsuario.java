package com.compartir.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo que representa la ubicación geográfica de un usuario.
 * Incluye información sobre la ciudad, provincia, país y continente.
 *
 * @author Sergio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionUsuario {
    private String ciudad;
    private String provincia;
    private String pais;
    private String continente;
}
