package com.app.web.service;

import com.app.web.dao.ListaRepository;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.dto.ListaDTO;
import com.app.web.entity.Lista;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa de servicio para operaciones de Listas
 * Maneja toda la lógica de negocio relacionada con las listas de usuarios
 */
@Service
public class ListasService extends DTOService<ListaDTO, Lista> {

    @Autowired
    private ListaRepository listaRepository;
    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

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

    /**
     * Obtener todas las listas como entidades
     *
     * @return Lista de entidades Lista
     */
    public List<Lista> getAllListas() {
        return listaRepository.findAll();
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
}
