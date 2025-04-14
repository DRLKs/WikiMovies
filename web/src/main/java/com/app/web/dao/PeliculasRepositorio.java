package com.app.web.dao;

import com.app.web.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PeliculasRepositorio extends JpaRepository<Pelicula, Integer> {

    @Query("select P from Pelicula P where lower( P.titulo ) like lower( concat( '%', :titulo, '%'))")
    public List<Pelicula> findByTitulo(@Param("titulo") String titulo);
}
