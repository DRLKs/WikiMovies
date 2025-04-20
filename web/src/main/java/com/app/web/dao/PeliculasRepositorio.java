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

    @Query("select p from Pelicula p where p.id = :id")
    public Pelicula getPeliculaById(@Param("id") Integer id);

    @Query("SELECT DISTINCT p FROM Pelicula p JOIN p.generos g " +
            "WHERE LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) " +
            "AND g.id IN :listaGeneros")
    List<Pelicula> findByGeneroTitulo(Integer[] listaGeneros, String titulo);
}
