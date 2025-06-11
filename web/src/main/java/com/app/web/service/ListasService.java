package com.app.web.service;

import com.app.web.dao.ListaRepository;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.dao.PeliculasRepository;
import com.app.web.dto.GeneroDTO;
import com.app.web.dto.ListaDTO;
import com.app.web.entity.Genero;
import com.app.web.entity.Lista;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.app.web.utils.Constantes.*;

/**
 * Capa de servicio para operaciones de Listas
 * Maneja toda la lógica de negocio relacionada con las listas de usuarios
 */
@Service
public class ListasService extends DTOService<ListaDTO, Lista> {

    @Autowired private ListaRepository listaRepository;
    @Autowired private UsuariosRepositorio usuariosRepositorio;
    @Autowired private PeliculasRepository peliculaRepository;


    /**
     * Obtener todas las listas como DTOs
     * 
     * @return Lista de objetos ListaDTO
     */
    public List<ListaDTO> getAllListasDTO() {
        List<Lista> listas = listaRepository.findAll();
        List<ListaDTO> listasDTOs = new ArrayList<>();

        for (Lista lista : listas) {
            listasDTOs.add(lista.toDTO());
        }

        return listasDTOs;
    }

    public List<ListaDTO> getListasPopularesDTO() {
        List<Lista> listas = listaRepository.listasPopulares();
        return this.entity2DTO(listas);
    }

    /**
     * Obtener todas las listas como entidades
     *
     * @return Lista de entidades Lista
     */
    public List<Lista> getAllListas() {
        return listaRepository.findAll();
    }


    /**
     * Obtenemos las listas de un usuario
     * @param idUsuario Identificador del usuario
     * @return List<ListaDTO>
     */
    public List<ListaDTO> getListasUsuario(Integer idUsuario) {
        Set<Lista> listas = usuariosRepositorio.getReferenceById(idUsuario).getListas();

        return this.entity2DTO(listas);
    }


    /**
     * Obtener una lista por su ID como DTO
     * 
     * @param id ID de la lista
     * @return Objeto ListaDTO o null si no se encuentra
     */
    public ListaDTO getListaDTOById(int id) {
        if (id <= 0) {
            return null;
        }

        Lista lista = listaRepository.findById(id).orElse(null);
        return lista != null ? lista.toDTO() : null;
    }

    /**
     * Obtener una lista por su ID como entidad
     *
     * @param id ID de la lista
     * @return Entidad Lista o null si no se encuentra
     */
    public Lista getListaById(int id) {
        if (id <= 0) {
            return null;
        }

        return listaRepository.findById(id).orElse(null);
    }

    /**
     * Obtener la lista de favoritos de un usuario como DTO
     * 
     * @param userId ID del usuario
     * @return ListaDTO de favoritos o null si no se encuentra
     */
    public ListaDTO getListaFavoritasDTO(int userId) {
        if (userId <= 0) {
            return null;
        }

        Usuario usuario = usuariosRepositorio.findById(userId).orElse(null);
        if (usuario == null) {
            return null;
        }

        Lista lista = listaRepository.getListaFavoritas(userId);
        return lista != null ? lista.toDTO() : null;
    }

    /**
     * Obtener la lista de favoritos de un usuario como entidad
     *
     * @param userId ID del usuario
     * @return Entidad Lista de favoritos o null si no se encuentra
     */
    public Lista getListaFavoritas(int userId) {
        if (userId <= 0) {
            return null;
        }

        return listaRepository.getListaFavoritas(userId);
    }

    /**
     * Obtener la lista de películas vistas de un usuario como DTO
     * 
     * @param userId ID del usuario
     * @return ListaDTO de películas vistas o null si no se encuentra
     */
    public ListaDTO getListaVistasDTO(int userId) {
        if (userId <= 0) {
            return null;
        }

        Lista lista = listaRepository.getListaVistas(userId);
        return lista != null ? lista.toDTO() : null;
    }

    /**
     * Obtener la lista de películas vistas de un usuario como entidad
     *
     * @param userId ID del usuario
     * @return Entidad Lista de películas vistas o null si no se encuentra
     */
    public Lista getListaVistas(int userId) {
        if (userId <= 0) {
            return null;
        }

        return listaRepository.getListaVistas(userId);
    }

    /**
     * Obtener todas las listas creadas por un usuario específico como DTOs
     * 
     * @param userId ID del usuario
     * @return Lista de objetos ListaDTO o lista vacía si no se encuentra ninguna
     */
    public List<ListaDTO> getListasDTOByUsuario(int userId) {
        if (userId <= 0) {
            return new ArrayList<>();
        }

        List<Lista> listas = listaRepository.getListasUsuario(userId);

        if (listas == null) {
            return new ArrayList<>();
        }

        List<ListaDTO> listasDTOs = new ArrayList<>();
        for (Lista lista : listas) {
            listasDTOs.add(lista.toDTO());
        }

        return listasDTOs;
    }

    /**
     * Obtener todas las listas creadas por un usuario específico como entidades
     *
     * @param userId ID del usuario
     * @return Lista de entidades Lista o lista vacía si no se encuentra ninguna
     */
    public List<Lista> getListasByUsuario(int userId) {
        if (userId <= 0) {
            return new ArrayList<>();
        }

        List<Lista> listas = listaRepository.getListasUsuario(userId);
        return listas != null ? listas : new ArrayList<>();
    }

    /**
     * Guardar o actualizar una lista y devolver DTO
     * 
     * @param lista Entidad Lista a guardar
     * @return El DTO de la lista guardada
     */
    public ListaDTO guardarListaDTO(Lista lista) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }

        Lista guardada = listaRepository.save(lista);
        return guardada.toDTO();
    }

    /**
     * Guardar o actualizar una lista
     * 
     * @param lista Entidad Lista a guardar
     * @return La entidad Lista guardada
     */
    public Lista guardarLista(Lista lista) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }

        return listaRepository.save(lista);
    }

    /**
     * Eliminar una lista por su ID
     * 
     * @param id ID de la lista a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean deleteListaById(int id) {
        if (id <= 0) {
            return false;
        }

        if (listaRepository.existsById(id)) {
            listaRepository.deleteById(id);
            return true;
        }

        return false;
    }

    /**
     * Añadir una película a una lista y devolver DTO
     * 
     * @param listaId  ID de la lista
     * @param pelicula Película a añadir
     * @return DTO de la lista actualizada o null si no se encuentra la lista
     */
    public ListaDTO addPeliculaToListaDTO(int listaId, Pelicula pelicula) {
        Lista lista = addPeliculaToLista(listaId, pelicula);
        return lista != null ? lista.toDTO() : null;
    }

    /**
     * Añadir una película a una lista
     * 
     * @param listaId  ID de la lista
     * @param pelicula Película a añadir
     * @return Lista actualizada o null si no se encuentra la lista
     */
    public Lista addPeliculaToLista(int listaId, Pelicula pelicula) {
        if (listaId <= 0 || pelicula == null) {
            return null;
        }

        Lista lista = listaRepository.findById(listaId).orElse(null);
        if (lista != null) {
            lista.getPeliculas().add(pelicula);
            return listaRepository.save(lista);
        }

        return null;
    }

    /**
     * Eliminar una película de una lista y devolver DTO
     * 
     * @param listaId    ID de la lista
     * @param peliculaId ID de la película a eliminar
     * @return DTO de la lista actualizada o null si no se encuentra la lista
     */
    public ListaDTO removePeliculaFromListaDTO(int listaId, int peliculaId) {
        Lista lista = removePeliculaFromLista(listaId, peliculaId);
        return lista != null ? lista.toDTO() : null;
    }

    /**
     * Eliminar una película de una lista
     * 
     * @param listaId    ID de la lista
     * @param peliculaId ID de la película a eliminar
     * @return Lista actualizada o null si no se encuentra la lista
     */
    public Lista removePeliculaFromLista(int listaId, int peliculaId) {
        if (listaId <= 0 || peliculaId <= 0) {
            return null;
        }

        Lista lista = listaRepository.findById(listaId).orElse(null);
        if (lista != null) {
            lista.getPeliculas().removeIf(p -> p.getId() == peliculaId);
            return listaRepository.save(lista);
        }

        return null;
    }

    /**
     * Guardar o actualizar una lista a partir de un DTO
     * 
     * @param listaDTO DTO de Lista a guardar
     * @return El DTO de la lista guardada
     */
    public ListaDTO guardarListaDTO(ListaDTO listaDTO) {

        if (listaDTO == null) {
            throw new IllegalArgumentException("La listaDTO no puede ser nula");
        }

        // Buscar la lista existente o crear una nueva
        Lista lista = listaDTO.getId() != null ? listaRepository.findById(listaDTO.getId()).orElse(new Lista())
                : new Lista();

        // Copiar propiedades básicas del DTO a la entidad
        lista.setId(listaDTO.getId());
        lista.setNombre(listaDTO.getNombre());
        lista.setDescripcion(listaDTO.getDescripcion());
        lista.setImgURL(listaDTO.getFotoUrl());

        // Buscar el usuario si tenemos un ID
        if (listaDTO.getUsuarioId() != null) {
            Usuario usuario = usuariosRepositorio.findById(listaDTO.getUsuarioId()).orElse(null);
            lista.setIdUsuario(usuario);
        }

        // Procesar las películas - esto requiere cargarlas por ID
        // Esta implementación podría necesitar optimización si hay muchas películas
        Set<Pelicula> peliculasSet = new LinkedHashSet<>();
        if (listaDTO.getPeliculasId() != null) {
            for (Integer peliculaId : listaDTO.getPeliculasId()) {
                // Esto asume que hay un método para obtener películas por ID
                // Se debería inyectar el servicio de películas si es necesario
                Pelicula pelicula = peliculaRepository.findById(peliculaId).orElse(null);
                if (pelicula != null) {
                    peliculasSet.add(pelicula);
                }
            }
            lista.setPeliculas(peliculasSet);
        }

        // Guardar la lista actualizada
        Lista guardada = listaRepository.save(lista);
        return guardada.toDTO();
    }

    // Función que devuelve una lista con los 2 géneros más repetidos de una lista
    public List<GeneroDTO> generosMasRepetidosEnLaLista(ListaDTO listaDTO) {
        // Buscar la lista existente
        Lista lista = listaRepository.findById(listaDTO.getId()).orElse(null);

        Set<Pelicula> peliculasLista = lista.getPeliculas();
        if (peliculasLista == null || peliculasLista.isEmpty()) return Collections.emptyList();

        // Mapa para contar frecuencia de cada género
        Map<GeneroDTO, Integer> generoFrecuencia = new HashMap<>();

        for (Pelicula pelicula : peliculasLista) {
            for (Genero genero : pelicula.getGeneros()) {
                generoFrecuencia.put(genero.toDTO(), generoFrecuencia.getOrDefault(genero, 0) + 1);
            }
        }

        // Ordenar por frecuencia descendente y tomar los 2 primeros
        List<GeneroDTO> generosTop2 = generoFrecuencia.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Orden descendente
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return generosTop2;
    }

    public Lista nuevaLista(String nombreLista, String descripcion, String imgURL, Usuario usuario){
        Lista lista = new Lista();
        lista.setNombre(nombreLista);
        lista.setIdUsuario(usuario);
        lista.setDescripcion(descripcion);
        lista.setImgURL(imgURL);
        lista.setPeliculas(new HashSet<>());
        return lista;
    }


    /**
     * Cuando un usuario toca el botón de que le gusta una película
     * @param peliculaId Identificador de la películas
     * @param usuarioId Identificador del usuario
     */
    public void peliculaAccionFavorita(Integer peliculaId, Integer usuarioId){

        // Obtenemos la lista por el usuario
        Lista listaFavorita = listaRepository.getListaFavoritas(usuarioId);
        Pelicula peliculaFavorita = peliculaRepository.getReferenceById(peliculaId);

        // En el caso de que el usuario no tenga la lista
        if( listaFavorita == null ){
            Usuario usuario = usuariosRepositorio.getReferenceById(usuarioId);
            listaFavorita = nuevaLista(LISTA_FAVORITAS,DESCRIPCION_LISTA_FAVORITAS,IMAGEN_LISTA_FAVORITAS,usuario);

            // Añadimos la película
            listaFavorita.getPeliculas().add(peliculaFavorita);
            listaRepository.save(listaFavorita);
            usuario.getListas().add(listaFavorita);

            // Guardamos los cambios
            usuariosRepositorio.save(usuario);

        }else{
            // Comprobar si la película ya está en vistas
            if (listaFavorita.getPeliculas().contains(peliculaFavorita)) {
                // Ya estaba vista y la quitamos
                listaFavorita.getPeliculas().remove(peliculaFavorita);
            } else {
                // La introducimos como vista
                listaFavorita.getPeliculas().add(peliculaFavorita);
            }
            listaRepository.save(listaFavorita);
        }
    }


    /**
     * Cuando un usuario toca el botón de que ha visto una película
     * @param peliculaId Identificador de la películas
     * @param usuarioId Identificador del usuario
     */
    public void peliculaAccionVer(Integer peliculaId, Integer usuarioId){

        // Obtenemos la lista por el usuario
        Lista listaVistas = listaRepository.getListaVistas(usuarioId);
        Pelicula peliculaVista = peliculaRepository.getReferenceById(peliculaId);

        // En el caso de que el usuario no tenga la lista
        if( listaVistas == null ){
            Usuario usuario = usuariosRepositorio.getReferenceById(usuarioId);
            listaVistas = nuevaLista(LISTA_VISTAS,DESCRIPCION_LISTA_VISTAS,IMAGEN_LISTA_VISTAS,usuario);

            // Añadimos la película
            listaVistas.getPeliculas().add(peliculaVista);
            listaRepository.save(listaVistas);
            usuario.getListas().add(listaVistas);

            // Guardamos los cambios
            usuariosRepositorio.save(usuario);

        }else{
            // Comprobar si la película ya está en vistas
            if (listaVistas.getPeliculas().contains(peliculaVista)) {
                // Ya estaba vista y la quitamos
                listaVistas.getPeliculas().remove(peliculaVista);
            } else {
                // La introducimos como vista
                listaVistas.getPeliculas().add(peliculaVista);
            }
            listaRepository.save(listaVistas);
        }
    }

}
