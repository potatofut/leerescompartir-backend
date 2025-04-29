package com.compartir.libros.service;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.compartir.libros.dto.libro.CambioEstadoRequest;
import com.compartir.libros.dto.libro.LibroDTO;
import com.compartir.libros.dto.libro.LibroRequestDTO;
import com.compartir.libros.dto.libro.LibroResponseDTO;
import com.compartir.libros.model.Libro;
import com.compartir.libros.model.Tematica;
import com.compartir.libros.model.Usuario;
import com.compartir.libros.repository.TematicaRepository;
import com.compartir.libros.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroService {
    private final UsuarioRepository usuarioRepository;
    private final TematicaRepository tematicaRepository;

    public List<LibroResponseDTO> obtenerLibrosUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuario.getLibros().stream()
            .map(libro -> convertirALibroResponseDTO(libro)).toList();
    }

    public LibroResponseDTO agregarLibro(String email, LibroRequestDTO libroRequest) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que las temáticas existan
        List<Tematica> tematicas = tematicaRepository.findByNombreIn(libroRequest.getTematicas());
        if (tematicas.size() != libroRequest.getTematicas().size()) {
            throw new RuntimeException("Una o más temáticas no existen");
        }

        Libro libro = new Libro();
        libro.setTitulo(libroRequest.getTitulo());
        libro.setAutor(libroRequest.getAutor());
        libro.setEstado("disponible");
        libro.setDescripcion(libroRequest.getDescripcion());
        libro.setPortada(libroRequest.getPortada());
        libro.setTematicas(tematicas.stream().map(Tematica::getId).toList());
        libro.setReservas(new ArrayList<>());

        usuario.getLibros().add(libro);
        usuarioRepository.save(usuario);

        return convertirALibroResponseDTO(libro);
    }

    public LibroResponseDTO actualizarLibro(String email, int indice, LibroRequestDTO libroRequest) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (indice < 0 || indice >= usuario.getLibros().size()) {
            throw new RuntimeException("Índice de libro no válido");
        }

        // Verificar que las temáticas existan
        List<Tematica> tematicas = tematicaRepository.findByNombreIn(libroRequest.getTematicas());
        if (tematicas.size() != libroRequest.getTematicas().size()) {
            throw new RuntimeException("Una o más temáticas no existen");
        }

        Libro libro = usuario.getLibros().get(indice);
        libro.setTitulo(libroRequest.getTitulo());
        libro.setAutor(libroRequest.getAutor());
        libro.setDescripcion(libroRequest.getDescripcion());
        libro.setPortada(libroRequest.getPortada());
        libro.setTematicas(tematicas.stream().map(Tematica::getId).toList());

        usuarioRepository.save(usuario);
        return convertirALibroResponseDTO(libro);
    }

    public void eliminarLibro(String email, int indice) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (indice < 0 || indice >= usuario.getLibros().size()) {
            throw new RuntimeException("Índice de libro no válido");
        }

        usuario.getLibros().remove(indice);
        usuarioRepository.save(usuario);
    }

    public LibroResponseDTO cambiarEstadoLibro(String email, int indice, CambioEstadoRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (indice < 0 || indice >= usuario.getLibros().size()) {
            throw new RuntimeException("Índice de libro no válido");
        }

        String nuevoEstado = request.getNuevoEstado().toLowerCase();
        if (!nuevoEstado.equals("disponible") && !nuevoEstado.equals("prestado") && !nuevoEstado.equals("reservado")) {
            throw new RuntimeException("Estado no válido. Debe ser: disponible, prestado o reservado");
        }

        Libro libro = usuario.getLibros().get(indice);
        libro.setEstado(nuevoEstado);

        usuarioRepository.save(usuario);
        return convertirALibroResponseDTO(libro);
    }

    public List<LibroDTO> buscarLibrosPorTematica(String tematicaId, String estado, String pais, String provincia, String ciudad) {
        List<Usuario> usuarios;
        ObjectId tematicaObjectId = new ObjectId(tematicaId);

        // Filtrar por región y estado si se especifican
        if (estado != null && !estado.equalsIgnoreCase("todos")) {
            if (pais != null && !pais.equalsIgnoreCase("todos")) {
                usuarios = usuarioRepository.findByRegionPaisAndLibrosTematicasAndEstado(pais, tematicaObjectId, estado);
            } else {
                usuarios = usuarioRepository.findByLibrosTematicasAndEstado(tematicaObjectId, estado);
            }
        } else {
            // Solo filtrar por región
            if (pais != null && !pais.equalsIgnoreCase("todos")) {
                usuarios = usuarioRepository.findByRegionPaisAndLibrosTematicas(pais, tematicaObjectId);
            } else if (provincia != null && !provincia.equalsIgnoreCase("todos")) {
                usuarios = usuarioRepository.findByRegionProvinciaAndLibrosTematicas(provincia, tematicaObjectId);
            } else if (ciudad != null && !ciudad.equalsIgnoreCase("todos")) {
                usuarios = usuarioRepository.findByRegionCiudadAndLibrosTematicas(ciudad, tematicaObjectId);
            } else {
                usuarios = usuarioRepository.findByLibrosTematicas(tematicaObjectId);
            }
        }

        return convertirALibrosDTO(usuarios, tematicaId, estado);
    }

    public List<LibroDTO> buscarLibrosPorTitulo(String titulo) {
        List<Usuario> usuarios = usuarioRepository.findByLibrosTituloContainingIgnoreCase(titulo);
        List<LibroDTO> libros = convertirALibrosDTO(usuarios, null, null);
        return libros.stream()
            .filter(libro -> libro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
            .toList();
    }

    public List<LibroDTO> buscarLibrosPorAutor(String autor) {
        List<Usuario> usuarios = usuarioRepository.findByLibrosAutorContainingIgnoreCase(autor);
        List<LibroDTO> libros = convertirALibrosDTO(usuarios, null, null);
        return libros.stream()
            .filter(libro -> libro.getAutor().toLowerCase().contains(autor.toLowerCase()))
            .toList();
    }

    private List<LibroDTO> convertirALibrosDTO(List<Usuario> usuarios, String tematicaId, String estadoFiltro) {
        List<LibroDTO> librosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            for (Libro libro : usuario.getLibros()) {
                // Aplicar filtros si son necesarios
                if ((tematicaId == null || libro.getTematicas().contains(new ObjectId(tematicaId))) && 
                    (estadoFiltro == null || estadoFiltro.equalsIgnoreCase("todos") || 
                     libro.getEstado().equalsIgnoreCase(estadoFiltro))) {
                    
                    LibroDTO libroDTO = new LibroDTO(
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getDescripcion(),
                        libro.getPortada(),
                        libro.getEstado(),
                        libro.getTematicas().stream()
                                            .map(ObjectId::toHexString)
                                            .toList(),
                        libro.getReservas(),
                        usuario.getNombre(),
                        usuario.getRegion().getCiudad(),
                        usuario.getRegion().getProvincia(),
                        usuario.getRegion().getPais()
                    );
                    librosDTO.add(libroDTO);
                }
            }
        }

        return librosDTO;
    }

    private LibroResponseDTO convertirALibroResponseDTO(Libro libro) {
        return new LibroResponseDTO(
            libro.getTitulo(),
            libro.getAutor(),
            libro.getEstado(),
            libro.getDescripcion(),
            libro.getPortada(),
            libro.getTematicas().stream()
                .map(ObjectId::toHexString)
                .toList(),
            libro.getReservas()
        );
    }
}