package com.app.web.dao;

import com.app.web.entity.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListaRepository extends JpaRepository<Lista,Integer> {

    @Query("SELECT l FROM Usuario u JOIN u.listas l WHERE u.id = :idUsuario AND l.nombre = 'Favoritas'")
    Lista getListaFavoritas(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT l FROM Usuario u JOIN u.listas l WHERE u.id = :idUsuario AND l.nombre = 'Vistas'")
    Lista getListaVistas(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT l FROM Lista l WHERE l.idUsuario.id = :idUsuario AND l.nombre NOT IN ('Favoritas', 'Vistas')")
    List<Lista> getListasUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT L FROM Lista L WHERE L.nombre != 'Favoritas' and L.nombre != 'Vistas'")
    List<Lista> listasPopulares();

    @Query("SELECT l FROM Lista l WHERE l.idUsuario.id = :idUsuario ORDER BY l.id")
    List<Lista> getTodasListasUsuario(@Param("idUsuario") Integer idUsuario);

}
