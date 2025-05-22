package com.app.web.service;

import com.app.web.dao.PeliculasRepository;
import com.app.web.entity.Genero;
import com.app.web.entity.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeliculasService {

    @Autowired private PeliculasRepository peliculasRepository;

    public List<Pelicula> listarPeliculas(){
        return peliculasRepository.findAll();
    }

    public List<Pelicula> buscarPeliculaXTitulo(String nombre){
        List<Pelicula> peliculas = new ArrayList<>();
        if(nombre != null){
           peliculas = peliculasRepository.findByTitulo(nombre);
        }
        return peliculas;
    }

    public List<Pelicula> buscarPeliculaXTituloYGenero(String nombre, Integer[] listaGeneros){
        List<Pelicula> peliculas = new ArrayList<>();
        if(nombre != null && listaGeneros != null){
            peliculas = peliculasRepository.findByGeneroTitulo(listaGeneros,nombre);
        }
        return peliculas;
    }

    public Pelicula buscarPelicula(int id){
        Pelicula pelicula = null;
        if(id > 0){
            pelicula = peliculasRepository.findById(id).orElse(null);
        }
        return pelicula;
    }

    public void eliminarPelicula(Integer id){
        if(id > 0){
            peliculasRepository.deleteById(id);
        }
    }

    public List<Pelicula> buscarPeliculaXPopularidad(PageRequest pageable){
        List<Pelicula> peliculas = new ArrayList<>();
        if(pageable != null && pageable.getPageSize() >= 1){
            peliculas = peliculasRepository.findTopPeliculasByPopularidad(pageable);
        }

        return peliculas;
    }



}
