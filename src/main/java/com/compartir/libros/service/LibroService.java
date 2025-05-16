package com.compartir.libros.service;

import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.compartir.libros.dto.libro.CambioEstadoRequest;
import com.compartir.libros.dto.libro.LibroDTO;
import com.compartir.libros.dto.libro.LibroPrestamoDTO;
import com.compartir.libros.dto.libro.LibroRequestDTO;
import com.compartir.libros.dto.libro.LibroReservaRequestDTO;
import com.compartir.libros.dto.libro.LibroResponseDTO;
import com.compartir.libros.model.Libro;
import com.compartir.libros.model.Reserva;
import com.compartir.libros.model.Tematica;
import com.compartir.libros.model.Usuario;
import com.compartir.libros.repository.TematicaRepository;
import com.compartir.libros.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio que gestiona todas las operaciones relacionadas con los libros.
 * Incluye funcionalidades para agregar, actualizar, eliminar y buscar libros,
 * así como gestionar préstamos y reservas.
 *
 * @author Sergio
 */
@Service
@RequiredArgsConstructor
public class LibroService {
    private final UsuarioRepository usuarioRepository;
    private final TematicaRepository tematicaRepository;

    /**
     * Obtiene todos los libros de un usuario específico.
     *
     * @param email Email del usuario
     * @return Lista de libros del usuario en formato DTO
     * @throws RuntimeException si el usuario no existe
     */
    public List<LibroResponseDTO> obtenerLibrosUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuario.getLibros().stream()
            .map(libro -> convertirALibroResponseDTO(libro)).toList();
    }

    /**
     * Agrega un nuevo libro a la colección de un usuario.
     *
     * @param email Email del usuario
     * @param libroRequest Datos del libro a agregar
     * @return Libro agregado en formato DTO
     * @throws RuntimeException si el usuario no existe o las temáticas no son válidas
     */
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

    /**
     * Actualiza la información de un libro existente.
     *
     * @param email Email del usuario
     * @param indice Índice del libro en la lista del usuario
     * @param libroRequest Nuevos datos del libro
     * @return Libro actualizado en formato DTO
     * @throws RuntimeException si el usuario no existe, el índice es inválido o las temáticas no son válidas
     */
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

    /**
     * Elimina un libro de la colección de un usuario.
     *
     * @param email Email del usuario
     * @param indice Índice del libro a eliminar
     * @throws RuntimeException si el usuario no existe o el índice es inválido
     */
    public void eliminarLibro(String email, int indice) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (indice < 0 || indice >= usuario.getLibros().size()) {
            throw new RuntimeException("Índice de libro no válido");
        }

        usuario.getLibros().remove(indice);
        usuarioRepository.save(usuario);
    }

    /**
     * Cambia el estado de un libro (disponible, prestado, reservado).
     *
     * @param email Email del usuario
     * @param indice Índice del libro
     * @param request Solicitud con el nuevo estado
     * @return Libro actualizado en formato DTO
     * @throws RuntimeException si el usuario no existe, el índice es inválido o el estado no es válido
     */
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

        if(nuevoEstado.equals("prestado")) {
            libro.getReservas().forEach(reserva -> {
                if(reserva.getFechaPrestamo() == null) {
                    reserva.setFechaPrestamo(new Date());
                }
            });
        }
        else if(nuevoEstado.equals("disponible")) {
            libro.getReservas().forEach(reserva -> {
                if(reserva.getFechaDevolucion() == null) {
                    reserva.setFechaDevolucion(new Date());
                }
            });
        }

        usuarioRepository.save(usuario);
        return convertirALibroResponseDTO(libro);
    }

    /**
     * Filtra libros según diferentes criterios.
     *
     * @param tematicaId ID de la temática
     * @param estado Estado del libro
     * @param pais País del usuario
     * @param provincia Provincia del usuario
     * @param ciudad Ciudad del usuario
     * @return Lista de libros filtrados
     */
    public List<LibroDTO> filtrarLibros(String tematicaId, String estado, String pais, String provincia, String ciudad) {
        List<Usuario> usuarios;
        ObjectId tematicaObjectId = tematicaId != null ? new ObjectId(tematicaId) : null;
    
        // Si no hay temática especificada, obtener todos los usuarios
        if (tematicaId == null) {
            if (estado != null && !estado.equalsIgnoreCase("todos")) {
                if (pais != null && !pais.equalsIgnoreCase("todos")) {
                    usuarios = usuarioRepository.findByRegionPaisAndLibrosEstado(pais, estado);
                } else {
                    usuarios = usuarioRepository.findByLibrosEstado(estado);
                }
            } else {
                if (pais != null && !pais.equalsIgnoreCase("todos")) {
                    usuarios = usuarioRepository.findByRegionPais(pais);
                } else if (provincia != null && !provincia.equalsIgnoreCase("todos")) {
                    usuarios = usuarioRepository.findByRegionProvincia(provincia);
                } else if (ciudad != null && !ciudad.equalsIgnoreCase("todos")) {
                    usuarios = usuarioRepository.findByRegionCiudad(ciudad);
                } else {
                    usuarios = usuarioRepository.findAll();
                }
            }
        } else {
            // Mantener la lógica existente para cuando hay temática especificada
            if (estado != null && !estado.equalsIgnoreCase("todos")) {
                if (pais != null && !pais.equalsIgnoreCase("todos")) {
                    usuarios = usuarioRepository.findByRegionPaisAndLibrosTematicasAndEstado(pais, tematicaObjectId, estado);
                } else {
                    usuarios = usuarioRepository.findByLibrosTematicasAndEstado(tematicaObjectId, estado);
                }
            } else {
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
        }
    
        return convertirALibrosDTO(usuarios, tematicaId, estado);
    }

    /**
     * Busca libros por título o autor.
     *
     * @param searchString Término de búsqueda
     * @return Lista de libros que coinciden con la búsqueda
     */
    public List<LibroDTO> buscarLibros(String searchString) {
        List<Usuario> usuarios = usuarioRepository.findByLibrosTituloOrAutorContainingIgnoreCase(searchString);
        List<LibroDTO> libros = convertirALibrosDTO(usuarios, null, null);
        return libros.stream()
            .filter(libro -> libro.getTitulo().toLowerCase().contains(searchString.toLowerCase()) ||
                             libro.getAutor().toLowerCase().contains(searchString.toLowerCase()))
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
                        usuario.getEmail(),
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

    /**
     * Obtiene los libros que un usuario tiene prestados.
     *
     * @param email Email del usuario
     * @return Lista de libros prestados al usuario
     */
    public List<LibroPrestamoDTO> obtenerLibrosPrestados(String email) {
        List<Usuario> todosUsuarios = usuarioRepository.findAll();
        List<LibroPrestamoDTO> librosPrestados = new ArrayList<>();

        for (Usuario usuario : todosUsuarios) {
            for (Libro libro : usuario.getLibros()) {
                if ((libro.getEstado().equals("prestado") || libro.getEstado().equals("reservado")) &&
                    libro.getReservas() != null && !libro.getReservas().isEmpty()) {
                    
                    // Get the most recent reservation for this book
                    Reserva ultimaReserva = libro.getReservas().get(libro.getReservas().size() - 1);
                    
                    if (ultimaReserva.getEmailUsuario().equals(email) && 
                        ultimaReserva.getFechaDevolucion() == null) {
                        
                        LibroPrestamoDTO libroDTO = new LibroPrestamoDTO(
                            libro.getTitulo(),
                            libro.getAutor(),
                            libro.getDescripcion(),
                            libro.getPortada(),
                            libro.getEstado(),
                            usuario.getEmail(),
                            ultimaReserva.getFechaReserva(),
                            ultimaReserva.getFechaPrestamo(),
                            ultimaReserva.getFechaDevolucion()
                        );
                        librosPrestados.add(libroDTO);
                    }
                }
            }
        }

        return librosPrestados;
    }

    /**
     * Registra la devolución de un libro prestado.
     *
     * @param name Email del usuario que devuelve el libro
     * @param libroPrestamoRequest Información del libro a devolver
     * @throws RuntimeException si no se encuentra el libro o la reserva no es válida
     */
    public void devolverLibro(String name, LibroReservaRequestDTO libroPrestamoRequest) {
        Usuario usuario = usuarioRepository.findByEmail(libroPrestamoRequest.getEmailUsuario())
            .orElseThrow(() -> new RuntimeException("El libro a devolver no está asociado a ningún usuario!"));

        for (Libro libro : usuario.getLibros()) {
            if (libro.getTitulo().equals(libroPrestamoRequest.getTitulo())) {
                for (Reserva reserva : libro.getReservas()) {
                    if (reserva.getEmailUsuario().equals(name) && 
                        reserva.getFechaDevolucion() == null) {
                        
                        reserva.setFechaDevolucion(new Date());
                        libro.setEstado("disponible");
                        usuarioRepository.save(usuario);
                        return;
                    }
                }
            }
        }

        throw new RuntimeException("No se encontró el libro o la reserva no es válida.");
    }

    /**
     * Reserva un libro para un usuario.
     *
     * @param email Email del usuario que reserva
     * @param reserva Información de la reserva
     * @throws RuntimeException si el propietario no existe, el libro no existe o no está disponible
     */
    public void reservarLibro(String email, LibroReservaRequestDTO reserva) {
        // Buscar al usuario dueño del libro
        Usuario usuarioPropietario = usuarioRepository.findByEmail(reserva.getEmailUsuario())
            .orElseThrow(() -> new RuntimeException("Propietario del libro no encontrado"));
    
        // Encontrar el libro en la lista de libros del usuario
        Libro libroAReservar = usuarioPropietario.getLibros().stream()
            .filter(libro -> libro.getTitulo().equals(reserva.getTitulo()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    
        // Verificar que el libro esté disponible
        if (!libroAReservar.getEstado().equals("disponible")) {
            throw new RuntimeException("El libro no está disponible para reservar");
        }
    
        // Crear nueva reserva
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setFechaReserva(new Date());
        nuevaReserva.setEmailUsuario(email);
        nuevaReserva.setFechaPrestamo(null);
        nuevaReserva.setFechaDevolucion(null);
    
        // Añadir la reserva al libro y actualizar su estado
        libroAReservar.getReservas().add(nuevaReserva);
        libroAReservar.setEstado("reservado");
    
        // Guardar los cambios
        usuarioRepository.save(usuarioPropietario);
    }
}