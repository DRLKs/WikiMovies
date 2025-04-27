package com.app.web.controller;

import com.app.web.dao.GenerosRepository;
import com.app.web.dao.PeliculasRepositorio;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Genero;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class Controlador extends BaseControlador {

    @Autowired
    PeliculasRepositorio peliculasRepositorio;

    @Autowired
    UsuariosRepositorio usuarioRepositorio;

    @Autowired
    GenerosRepository generosRepositorio;

    @GetMapping("/")
    public String index(Model model) {

        List<Pelicula> peliculas = peliculasRepositorio.findAll();
        model.addAttribute("peliculas", peliculas);

        List<Genero> generos =generosRepositorio.findAll();
        model.addAttribute("generos", generos);

        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("title")String titulo,
                         @RequestParam(value = "generos", required = false ) Integer[] listaGeneros,
                         Model model) {

        List<Pelicula> peliculas = null;

        if( listaGeneros != null && listaGeneros.length > 0 ) { // Filtro de géneros
            peliculas = peliculasRepositorio.findByGeneroTitulo(listaGeneros,titulo);
        }else{
            peliculas = peliculasRepositorio.findByTitulo(titulo);
        }
        model.addAttribute("titulo", titulo);
        model.addAttribute("peliculas",peliculas);

        List<Genero> generos =generosRepositorio.findAll();
        model.addAttribute("generos", generos);

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
            Set<Pelicula> peliculasFavoritas = usuario.getPeliculasFavoritas();
            if (peliculasFavoritas != null){
                peliculaFavorita = peliculasFavoritas.contains(pelicula);
            }
        }
        
        model.addAttribute("peliculaFavorita", peliculaFavorita);

        return "film";
    }

    
    @PostMapping("/favorite")
    public String doFavorite(@RequestParam("id") Integer id, Model model, HttpServletRequest request, HttpSession session) {

        if( !estaAutenticado(request,session) ) {
            return "redirect:/login";
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Pelicula pelicula = peliculasRepositorio.getReferenceById(id);

        if ( usuario.getPeliculasFavoritas() == null ){
            usuario.setPeliculasFavoritas(new LinkedHashSet<>());
        }

        boolean peliculaFavorita = usuario.getPeliculasFavoritas().contains(pelicula);
        Set<Pelicula> peliculasActualizadas = usuario.getPeliculasFavoritas();
        Set<Usuario> usuariosActualizadosLista = pelicula.getUsuarios();

        if( peliculaFavorita ) {        // Ya es favorita

            peliculasActualizadas.remove(pelicula);
            usuario.setPeliculasFavoritas(peliculasActualizadas);

            usuariosActualizadosLista.remove(usuario);
            pelicula.setUsuarios(usuariosActualizadosLista);

        }else{                          // La introducimos como favorita

            peliculasActualizadas.add(pelicula);
            usuariosActualizadosLista.add(usuario);

            usuario.setPeliculasFavoritas(peliculasActualizadas);
            pelicula.setUsuarios(usuariosActualizadosLista);
        }

        this.peliculasRepositorio.save(pelicula);
        this.usuarioRepositorio.save(usuario);

        return "redirect:/film?id=" + id;
    }

    @GetMapping("/miembros")
    public String mostrarMiembros(Model model) {

        List<Usuario> usuarios = usuarioRepositorio.findAll();

        model.addAttribute("miembros", usuarios);

        List<Genero> generos =generosRepositorio.findAll();
        model.addAttribute("generos", generos);

        return "miembros";
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

    @GetMapping("/profile")
    public String mostrarProfile(@RequestParam("id") Integer id, Model model) {

        Usuario usuario = usuarioRepositorio.getReferenceById(id);
        model.addAttribute("usuario", usuario);

        List<Genero> generos =generosRepositorio.findAll();
        model.addAttribute("generos", generos);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String doUpdateProfile(@RequestParam(value="avatar", required = false) String avatar,
                                  @RequestParam(value = "nombreUsuario", required = false) String nombreUsuario,
                                  @RequestParam(value = "biografia", required = false) String biografia,
                                  @RequestParam(value = "fechaNacimiento", required = false) String fechaNacimiento,
                                  @RequestParam(value = "genero", required = false) Integer genero,
                                  Model model, HttpServletRequest request, HttpSession session) {

        if( !estaAutenticado(request,session) ) {
            return "redirect:/login";
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        try {

            if (avatar != null && !avatar.isEmpty()) {
                usuario.setAvatarUrl(avatar);
            }

            if (nombreUsuario != null && !nombreUsuario.isEmpty() && !nombreUsuario.equals(usuario.getNombreUsuario())) {
                usuario.setNombreUsuario(nombreUsuario);
            }

            if (biografia != null && !biografia.isEmpty() && !biografia.equals(usuario.getBiografia())) {
                usuario.setBiografia(biografia);
            }

            if( fechaNacimiento != null && !fechaNacimiento.isEmpty()) {

                usuario.setNacimientoFecha(parseFecha(fechaNacimiento));
            }

            if( genero != null ) {
                usuario.setGenero(genero);
            }

        }catch (Exception e) {      // A veces, las URLS son muy largas y da errores
            model.addAttribute("usuario", usuario);
            return "profile";
        }

        this.usuarioRepositorio.save(usuario);

        return "redirect:/profile?id=" + usuario.getId();
    }


    /**
     * Función para pasar de fecha al formato esperado
     * @param fecha Cadena de caracteres con la fecha
     * @return fecha en formato requerido por la base de datos
     */
    private Instant parseFecha(String fecha) {
        try {
            // Convertir de YYYY-MM-DD a un formato que Instant pueda parsear
            // Añadimos la hora (medianoche) y la zona horaria UTC
            return java.time.LocalDate.parse(fecha)
                    .atStartOfDay(java.time.ZoneOffset.UTC)
                    .toInstant();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // En caso de error, devolvemos null
        }
    }
}