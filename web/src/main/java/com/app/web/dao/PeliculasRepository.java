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
    public List<Pelicula> findByTitulo(@Param("titulo") String titulo);

    @Query("select p from Pelicula p where p.id = :id")
    public Pelicula getPeliculaById(@Param("id") Integer id);

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

    /**
     * Devuelve la lista de películas favoritas del usuario
     * @param usuario Usuario
     * @return Lista de películas favoritas
     */
    @Query("SELECT P FROM Pelicula P JOIN P.usuarios users WHERE :usuario = users.id")
    List<Pelicula> findPeliculasFavoritasByUsuario(int usuario);

    @Query("SELECT p FROM Pelicula p WHERE :genero MEMBER OF p.generos ORDER BY p.popularidad DESC, p.mediaVotos DESC")
    List<Pelicula> findPeliculasByGenero(@Param("genero") Genero genero, PageRequest pageable);
}
