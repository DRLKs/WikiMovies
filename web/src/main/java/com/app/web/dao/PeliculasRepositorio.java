package com.app.web.dao;

import com.app.web.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculasRepositorio extends JpaRepository<Pelicula, Integer> {

}
