package com.app.web.controller;

import com.app.web.dao.PeliculasRepositorio;
import com.app.web.dao.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class PeliculasController extends BaseControlador{

    @Autowired
    PeliculasRepositorio peliculasRepositorio;

    @GetMapping("/peliculas")
    public String ListarPeliculas(){
        return "peliculas";
    }

}
