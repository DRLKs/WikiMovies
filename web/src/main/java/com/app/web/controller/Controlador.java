package com.app.web.controller;

import com.app.web.dao.GenerosRepository;
import com.app.web.dao.PeliculasRepositorio;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Genero;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import com.app.web.ui.FiltroBusquedaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired PeliculasRepositorio peliculasRepositorio;
    @Autowired UsuariosRepositorio usuarioRepositorio;
    @Autowired GenerosRepository generosRepositorio;

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
    public String search(@ModelAttribute() FiltroBusquedaDTO filtroBusquedaDTO,
                         Model model, HttpSession session) {

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

        Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESION);

        if( usuario != null ) {
            // Asegurarnos de tener el usuario actualizado desde la base de datos
            List<Pelicula> peliculasFavoritas = peliculasRepositorio.findPeliculasFavoritasByUsuario(usuario.getId());
            if (peliculasFavoritas != null){
                peliculaFavorita = peliculasFavoritas.contains(pelicula);
            }
        }
        
        model.addAttribute( "peliculaFavorita", peliculaFavorita);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "film";
    }

    /**
     * El controlador recibe la acción de poner como favorita para el usuario la película entregada
     * @param id El identificador de la película
     */
    @PostMapping("/favorite")
    public String doFavorite(@RequestParam("id") Integer id, Model model, HttpServletRequest request, HttpSession session) {

        // Un usuario no puede guardarse una películas como favorita si tiene la sesión iniciada
        if( !estaAutenticado(request,session) ) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioRepositorio.getUsuarioById(idUsuario);

        Set<Pelicula> peliculasFavoritasActualizadas;
        if ( usuario.getPeliculasFavoritas() == null ){
            peliculasFavoritasActualizadas = new HashSet<>();
        }else{
            peliculasFavoritasActualizadas = usuario.getPeliculasFavoritas();
        }

        Pelicula pelicula = peliculasRepositorio.getReferenceById(id);

        if( peliculasFavoritasActualizadas.contains(pelicula) ) {   // Ya era favorita

            peliculasFavoritasActualizadas.remove(pelicula);
            usuario.setPeliculasFavoritas(peliculasFavoritasActualizadas);

            pelicula.getUsuarios().remove(usuario);

        }else{                                                      // La introducimos como favorita

            peliculasFavoritasActualizadas.add(pelicula);
            usuario.setPeliculasFavoritas(peliculasFavoritasActualizadas);
            
            pelicula.getUsuarios().add(usuario);
        }

        // Guardamos los cambios
        this.peliculasRepositorio.save(pelicula);
        this.usuarioRepositorio.save(usuario);

        return "redirect:/film?id=" + id;
    }

    @GetMapping("/peliculas")
    public String mostrarPeliculas(Model model, Pageable pageable) {
        List<Pelicula> peliculas = peliculasRepositorio.findTopPeliculasByPopularidad(PageRequest.of(0, 10));
        model.addAttribute("peliculas", peliculas);

        return "peliculas";
    }

    @GetMapping("/informacionPelicula")
    public String mostrarInformacionPelicula(@RequestParam("id") Integer id,Model model) {
        Pelicula pelicula = peliculasRepositorio.getPeliculaById(id);
        model.addAttribute("pelicula", pelicula);

        return "informacionPelicula";
    }

    @GetMapping("/favoriteMovies")
    public String mostrarLista(@RequestParam("usuarioId") Integer usuarioId, Model model){

        Usuario usuario = usuarioRepositorio.getUsuarioById(usuarioId);
        Set<Pelicula> peliculasFavoritas = usuario.getPeliculasFavoritas();

        model.addAttribute("usuarioLista", usuario);
        model.addAttribute("peliculasFavoritas", peliculasFavoritas);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "favoriteMovies";
    }

}