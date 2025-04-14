package com.app.web.controller;

import com.app.web.dao.PeliculasRepositorio;
import com.app.web.entity.Pelicula;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Controlador {

    @Autowired
    PeliculasRepositorio peliculasRepositorio;

    @GetMapping("/")
    public String index(Model model) {

        List<Pelicula> peliculas = peliculasRepositorio.findAll();
        model.addAttribute("peliculas", peliculas);

        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("title") String titulo, Model model) {

        List<Pelicula> peliculas = peliculasRepositorio.findByTitulo(titulo);
        model.addAttribute("titulo", titulo);
        model.addAttribute("peliculas",peliculas);

        return "search";
    }

    @GetMapping("/film")
    public String seeFilm(@RequestParam("id") Integer id, Model model) {

        Pelicula pelicula = peliculasRepositorio.getReferenceById(id);
        model.addAttribute("pelicula", pelicula);

        return "film";
    }

    @PostMapping("/favorite")
    public String doFavorite( @RequestParam("id") Integer id, Model model, HttpSession session) {
        return "/";
    }
}