package com.app.web.dao;

import com.app.web.entity.Genero;
import com.app.web.entity.Pelicula;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeliculasRepository extends JpaRepository<Pelicula, Integer> {

    @Query("select P from Pelicula P where lower( P.titulo ) like lower( concat( '%', :titulo, '%'))")
    List<Pelicula> findByTitulo(@Param("titulo") String titulo);

    @Query("select p from Pelicula p where p.id = :id")
    Pelicula getPeliculaById(@Param("id") Integer id);

    @Query("SELECT DISTINCT p FROM Pelicula p JOIN p.generos g " +
            "WHERE LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) " +
            "AND g.id IN :listaGeneros")
    List<Pelicula> findByGeneroTitulo(Integer[] listaGeneros, String titulo);

    /*
    * Pageable o PageRequest se usa para que solo carge x numero de peliculas, no toda la base de datos
    * FUNCIONALIDAD: List<Pelicula> topPeliculas = peliculaRepository
    .findTopPeliculasByPopularidad(PageRequest.of(0, 10));
    * Solo escoge el top 10
    * */
    @Query("SELECT p FROM Pelicula p ORDER BY p.popularidad DESC")
    List<Pelicula> findTopPeliculasByPopularidad(PageRequest pageable);

    @Query("SELECT DISTINCT p FROM Pelicula p JOIN p.generos g WHERE g.id = :idGenero1 OR g.id = :idGenero2")
    List<Pelicula> getPeliculasByGeneros(@Param("idGenero1") Integer idGenero1, @Param("idGenero2") Integer idGenero2);

    @Query("SELECT DISTINCT p FROM Pelicula p JOIN p.generos g WHERE g.id = :idGenero")
    List<Pelicula> getPeliculasByGenero(@Param("idGenero") Integer idGenero);

}
