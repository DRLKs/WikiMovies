package com.app.web.dao;

import com.app.web.entity.Favorito;
import com.app.web.entity.Pelicula;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritesPeliculasRepository extends CrudRepository<Favorito, Integer> {

    @Query("select p from Pelicula p join Favorito f on f.idPelicula = p.id where f.idUsuario = :id")
    List<Pelicula> peliculasFavoritas(Integer id);

}
