package com.app.web.controller;

import com.app.web.dao.PeliculasRepositorio;
import com.app.web.entity.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Controlador {

    @Autowired
    PeliculasRepositorio peliculasRepositorio;

    @GetMapping("/index")
    public String index(Model model) {

        List<Pelicula> peliculas = peliculasRepositorio.findAll();
        model.addAttribute("peliculas", peliculas);

        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {

        return "welcome";
    }
}