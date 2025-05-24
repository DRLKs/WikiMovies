package com.app.web.service;

import com.app.web.dao.PeliculasRepository;
import com.app.web.dto.PeliculaDTO;
import com.app.web.entity.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeliculasService extends DTOService<PeliculaDTO, Pelicula> {

    @Autowired
    private PeliculasRepository peliculasRepository;

    public List<Pelicula> listarPeliculas() {
        return peliculasRepository.findAll();
    }

    public List<PeliculaDTO> getAllPeliculasDTO() {
        List<Pelicula> peliculas = peliculasRepository.findAll();
        List<PeliculaDTO> peliculasDTOS = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            peliculasDTOS.add(pelicula.toDTO());
        }

        return peliculasDTOS;
    }

    public List<Pelicula> buscarPeliculaXTitulo(String nombre) {
        List<Pelicula> peliculas = new ArrayList<>();
        if (nombre != null) {
            peliculas = peliculasRepository.findByTitulo(nombre);
        }
        return peliculas;
    }

    public List<PeliculaDTO> getAllPeliculasDTOByTitulo(String titulo) {
        List<Pelicula> peliculas = buscarPeliculaXTitulo(titulo);
        List<PeliculaDTO> peliculasDTOs = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            peliculasDTOs.add(pelicula.toDTO());
        }

        return peliculasDTOs;
    }

    public List<Pelicula> buscarPeliculaXTituloYGenero(String nombre, Integer[] listaGeneros) {
        List<Pelicula> peliculas = new ArrayList<>();
        if (nombre != null && listaGeneros != null) {
            peliculas = peliculasRepository.findByGeneroTitulo(listaGeneros, nombre);
        }
        return peliculas;
    }

    public List<PeliculaDTO> getAllPeliculasDTOByTituloYGenero(String titulo, Integer[] listaGeneros) {
        List<Pelicula> peliculas = buscarPeliculaXTituloYGenero(titulo, listaGeneros);
        List<PeliculaDTO> peliculasDTOs = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            peliculasDTOs.add(pelicula.toDTO());
        }

        return peliculasDTOs;
    }

    public Pelicula buscarPelicula(int id) {
        Pelicula pelicula = null;
        if (id > 0) {
            pelicula = peliculasRepository.findById(id).orElse(null);
        }
        return pelicula;
    }

    public PeliculaDTO buscarPeliculaDTO(int id) {
        Pelicula pelicula = buscarPelicula(id);
        return pelicula != null ? pelicula.toDTO() : null;
    }

    public void eliminarPelicula(Integer id) {
        if (id > 0) {
            peliculasRepository.deleteById(id);
        }
    }

    public List<Pelicula> buscarPeliculaXPopularidad(PageRequest pageable) {
        List<Pelicula> peliculas = new ArrayList<>();
        if (pageable != null && pageable.getPageSize() >= 1) {
            peliculas = peliculasRepository.findTopPeliculasByPopularidad(pageable);
        }

        return peliculas;
    }

    public List<PeliculaDTO> buscarPeliculaDTOByPopularidad(PageRequest pageable) {
        List<Pelicula> peliculas = buscarPeliculaXPopularidad(pageable);
        List<PeliculaDTO> peliculasDTOs = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            peliculasDTOs.add(pelicula.toDTO());
        }

        return peliculasDTOs;
    }

}
