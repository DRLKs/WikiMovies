package com.app.web.controller;

import com.app.web.dao.PeliculasRepositorio;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Controlador extends BaseControlador {

    @Autowired
    PeliculasRepositorio peliculasRepositorio;

    @Autowired
    UsuariosRepositorio usuarioRepositorio;

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
    public String seeFilm(@RequestParam("id") Integer id, Model model, HttpSession session) {

        Pelicula pelicula = peliculasRepositorio.getPeliculaById(id);

        if (pelicula == null) {
            model.addAttribute("mensaje", "La película solicitada no existe");
            return "error";
        }
        model.addAttribute("pelicula", pelicula);


        // Para saber si esta película está entre sus favoritas
        boolean peliculaFavorita = false;
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if( usuario != null ) {
            // Asegurarnos de tener el usuario actualizado desde la base de datos
            fav
        }
        
        model.addAttribute("peliculaFavorita", peliculaFavorita);

        return "film";
    }

    
    @PostMapping("/favorite")
    public String doFavorite( @RequestParam("id") Integer id, Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if( usuario == null ) {
            return "redirect:/login";
        }

        Pelicula pelicula = peliculasRepositorio.getReferenceById(id);
        boolean peliculaFavorita = usuario.getPeliculas().contains(pelicula);

        if( peliculaFavorita ) {        // Ya es favorita
            usuario.getPeliculas().remove(pelicula);
            pelicula.getUsuarios().remove(usuario);
        }else{                          // La introducimos como favorita
            usuario.getPeliculas().add(pelicula);
            pelicula.getUsuarios().add(usuario);
        }

        this.peliculasRepositorio.save(pelicula);
        this.usuarioRepositorio.save(usuario);

        return "redirect:/film?id=" + id;
    }
}