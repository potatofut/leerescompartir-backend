package com.compartir.libros.service;

import com.compartir.libros.dto.tematica.TematicaDTO;
import com.compartir.libros.model.Tematica;
import com.compartir.libros.repository.TematicaRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TematicaServiceTest {

    @Mock
    private TematicaRepository tematicaRepository;

    @InjectMocks
    private TematicaService tematicaService;

    private Tematica tematica1;
    private Tematica tematica2;
    private final String idValida = "507f1f77bcf86cd799439011";

    @BeforeEach
    void setUp() {
        tematica1 = new Tematica();
        tematica1.setId(new ObjectId(idValida));
        tematica1.setNombre("Ficción");
        tematica1.setImagen("ficcion.jpg");
        tematica1.setDescripcion("Libros de ficción");

        tematica2 = new Tematica();
        tematica2.setId(new ObjectId());
        tematica2.setNombre("Ciencia");
        tematica2.setImagen("ciencia.jpg");
        tematica2.setDescripcion("Libros de ciencia");
    }

    @Test
    void obtenerTodasTematicas_DebeRetornarListaTematicasDTO() {
        // Arrange
        List<Tematica> tematicas = Arrays.asList(tematica1, tematica2);
        when(tematicaRepository.findAll()).thenReturn(tematicas);

        // Act
        List<TematicaDTO> resultado = tematicaService.obtenerTodasTematicas();

        // Assert
        assertEquals(2, resultado.size());
        
        TematicaDTO dto1 = resultado.get(0);
        assertEquals(tematica1.getId().toHexString(), dto1.getId());
        assertEquals(tematica1.getNombre(), dto1.getNombre());
        assertEquals(tematica1.getImagen(), dto1.getImagen());
        assertEquals(tematica1.getDescripcion(), dto1.getDescripcion());
        
        TematicaDTO dto2 = resultado.get(1);
        assertEquals(tematica2.getId().toHexString(), dto2.getId());
        assertEquals(tematica2.getNombre(), dto2.getNombre());
        assertEquals(tematica2.getImagen(), dto2.getImagen());
        assertEquals(tematica2.getDescripcion(), dto2.getDescripcion());

        verify(tematicaRepository, times(1)).findAll();
    }

    @Test
    void obtenerTodasTematicas_DebeRetornarListaVacia_CuandoNoHayTematicas() {
        // Arrange
        when(tematicaRepository.findAll()).thenReturn(List.of());

        // Act
        List<TematicaDTO> resultado = tematicaService.obtenerTodasTematicas();

        // Assert
        assertTrue(resultado.isEmpty());
        verify(tematicaRepository, times(1)).findAll();
    }

    @Test
    void obtenerTematicaPorId_DebeRetornarTematicaDTO_CuandoIdExiste() {
        // Arrange
        when(tematicaRepository.findById(new ObjectId(idValida))).thenReturn(Optional.of(tematica1));

        // Act
        TematicaDTO resultado = tematicaService.obtenerTematicaPorId(idValida);

        // Assert
        assertNotNull(resultado);
        assertEquals(tematica1.getId().toHexString(), resultado.getId());
        assertEquals(tematica1.getNombre(), resultado.getNombre());
        assertEquals(tematica1.getImagen(), resultado.getImagen());
        assertEquals(tematica1.getDescripcion(), resultado.getDescripcion());

        verify(tematicaRepository, times(1)).findById(new ObjectId(idValida));
    }

    @Test
    void obtenerTematicaPorId_DebeLanzarExcepcion_CuandoIdNoExiste() {
        // Arrange
        String idNoExistente = "000000000000000000000000";
        when(tematicaRepository.findById(new ObjectId(idNoExistente))).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> tematicaService.obtenerTematicaPorId(idNoExistente));
        
        assertEquals("Temática no encontrada", exception.getMessage());
        verify(tematicaRepository, times(1)).findById(new ObjectId(idNoExistente));
    }

    @Test
    void obtenerTematicaPorId_DebeLanzarExcepcion_CuandoIdEsInvalida() {
        // Arrange
        String idInvalida = "id-invalida";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> tematicaService.obtenerTematicaPorId(idInvalida));
        
        verify(tematicaRepository, never()).findById(any());
    }
}