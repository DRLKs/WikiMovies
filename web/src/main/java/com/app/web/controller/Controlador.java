package com.app.web.controller;

import com.app.web.dao.GenerosRepository;
import com.app.web.dao.ListaRepository;
import com.app.web.dao.PeliculasRepository;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Genero;
import com.app.web.entity.Lista;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import com.app.web.ui.FiltroBusquedaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import static com.app.web.utils.Constantes.USUARIO_SESION;

@Controller
public class Controlador extends BaseControlador {

    @Autowired
    PeliculasRepository peliculasRepositorio;
    @Autowired UsuariosRepositorio usuarioRepositorio;
    @Autowired GenerosRepository generosRepositorio;
    @Autowired protected ListaRepository listaRepository;

    /**
     * Controlador de la pantalla inicial
     */
    @GetMapping("/")
    public String index(Model model) {

        List<Pelicula> peliculas = peliculasRepositorio.findAll();
        model.addAttribute("peliculas", peliculas);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "index";
    }

    /**
     * Controlador del formulario sobre el filtro al buscar películas
     */
    @GetMapping("/search")
    public String search(@ModelAttribute() FiltroBusquedaDTO filtroBusquedaDTO, Model model, HttpServletRequest request, HttpSession session) {

        if(estaAutenticado(request,session)) {
            Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESION);
            Lista favoritas = usuarioRepositorio.getListaFavoritas(usuario.getId());
            model.addAttribute("favoritas", favoritas);
            Lista vistas  = usuarioRepositorio.getListaVistas(usuario.getId());
            model.addAttribute("vistas", vistas);
        }

        List<Pelicula> peliculas;
        Integer[] listaGeneros = filtroBusquedaDTO.getGeneros();
        String titulo = filtroBusquedaDTO.getTitulo();

        if( listaGeneros != null && listaGeneros.length > 0 ) { // Filtro de géneros
            peliculas = peliculasRepositorio.findByGeneroTitulo(listaGeneros,titulo);
        }else{
            peliculas = peliculasRepositorio.findByTitulo(titulo);
        }

        model.addAttribute("titulo", titulo);
        model.addAttribute("peliculas",peliculas);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", filtroBusquedaDTO);

        return "search";
    }

    /**
     * Controlador, muestra la información de una película concreta
     */
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
        boolean peliculaVista = false;

        Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESION);

        if( usuario != null ) {
            // Asegurarnos de tener el usuario actualizado desde la base de datos
            Lista peliculasFavoritas = usuarioRepositorio.getListaFavoritas(usuario.getId());
            peliculaFavorita = peliculasFavoritas.getPeliculas().contains(pelicula);
            Lista peliculasVistas = usuarioRepositorio.getListaVistas(usuario.getId());
            peliculaVista = peliculasVistas.getPeliculas().contains(pelicula);
        }
        
        model.addAttribute( "peliculaFavorita", peliculaFavorita);
        model.addAttribute( "peliculaVista", peliculaVista);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "film";
    }

    /**
     * El controlador recibe la acción de poner como favorita para el usuario la película entregada
     * @param idPelicula El identificador de la película
     */
    @PostMapping("/favorite")
    public String doFavorite(@RequestParam("idPelicula") Integer idPelicula, HttpServletRequest request, HttpSession session) {

        // Un usuario no puede guardarse una películas como favorita si tiene la sesión iniciada
        if( !estaAutenticado(request,session) ) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioRepositorio.getUsuarioById(idUsuario);

        Lista favoritas = usuarioRepositorio.getListaFavoritas(usuario.getId());
        Pelicula pelicula = peliculasRepositorio.getReferenceById(idPelicula);

        if( favoritas.getPeliculas().contains(pelicula) ) {   // Ya era favorita y la quitamos
            favoritas.getPeliculas().remove(pelicula);
        }else{                                                // La introducimos como favorita
            favoritas.getPeliculas().add(pelicula);
        }

        // Guardamos los cambios
        this.usuarioRepositorio.save(usuario);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/film?id=" + idPelicula);
    }

    @PostMapping("/seen")
    public String doSeen(@RequestParam("idPelicula") Integer idPelicula, HttpServletRequest request, HttpSession session) {

        // Un usuario no puede guardarse una películas como favorita si tiene la sesión iniciada
        if( !estaAutenticado(request,session) ) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioRepositorio.getUsuarioById(idUsuario);

        Lista vistas = usuarioRepositorio.getListaVistas(usuario.getId());
        Pelicula pelicula = peliculasRepositorio.getReferenceById(idPelicula);

        if( vistas.getPeliculas().contains(pelicula) ) {   // Ya estaba vista y la quitamos
            vistas.getPeliculas().remove(pelicula);
        }else{                                             // La introducimos como vista
            vistas.getPeliculas().add(pelicula);
        }

        // Guardamos los cambios
        this.usuarioRepositorio.save(usuario);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/film?id=" + idPelicula);
    }

    @GetMapping("/peliculas")
    public String mostrarPeliculas(Model model) {
        List<Pelicula> peliculas = peliculasRepositorio.findTopPeliculasByPopularidad(PageRequest.of(0, 10));
        model.addAttribute("peliculas", peliculas);

        // Para que la búsqueda y el filtro funcione
        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "peliculas";
    }

    @GetMapping("/informacionPelicula")
    public String mostrarInformacionPelicula(@RequestParam("id") Integer id,Model model) {
        Pelicula pelicula = peliculasRepositorio.getPeliculaById(id);
        model.addAttribute("pelicula", pelicula);

        return "informacionPelicula";
    }

    @GetMapping("/mostrarLista")
    public String mostrarLista(@RequestParam("listaId") Integer listaId, Model model, HttpServletRequest request, HttpSession session){

        // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioRepositorio.getUsuarioById(idUsuario);

        Lista lista = listaRepository.findById(listaId).get();

        model.addAttribute("usuarioLista", usuario);
        model.addAttribute("lista", lista);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "mostrarLista";
    }

}