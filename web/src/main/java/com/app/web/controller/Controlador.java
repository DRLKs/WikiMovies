package com.app.web.controller;

import com.app.web.dao.GenerosRepository;
import com.app.web.dto.ListaDTO;
import com.app.web.dto.PeliculaDTO;
import com.app.web.entity.Genero;
import com.app.web.entity.Lista;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import com.app.web.service.ListasService;
import com.app.web.service.PeliculasService;
import com.app.web.service.UsuarioService;
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
    PeliculasService peliculasService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GenerosRepository generosRepositorio;
    @Autowired
    protected ListasService listasService;

    /**
     * Controlador de la pantalla inicial
     */
    @GetMapping("/")
    public String index(Model model) {

        List<PeliculaDTO> peliculas = peliculasService.getAllPeliculasDTO();
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
    public String search(@ModelAttribute() FiltroBusquedaDTO filtroBusquedaDTO, Model model, HttpServletRequest request,
            HttpSession session) {

        if (estaAutenticado(request, session)) {
            Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESION);
            // Usar métodos DTO en lugar de entidades
            model.addAttribute("favoritas", listasService.getListaFavoritasDTO(usuario.getId()));
            model.addAttribute("vistas", listasService.getListaVistasDTO(usuario.getId()));
            model.addAttribute("listasUsuario", listasService.getListasDTOByUsuario(usuario.getId()));
        }

        List<PeliculaDTO> peliculas;
        Integer[] listaGeneros = filtroBusquedaDTO.getGeneros();
        String titulo = filtroBusquedaDTO.getTitulo();

        if (listaGeneros != null && listaGeneros.length > 0) { // Filtro de géneros
            peliculas = peliculasService.getAllPeliculasDTOByTituloYGenero(titulo, listaGeneros);
        } else {
            peliculas = peliculasService.getAllPeliculasDTOByTitulo(titulo);
        }

        model.addAttribute("titulo", titulo);
        model.addAttribute("peliculas", peliculas);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", filtroBusquedaDTO);

        return "search";
    }

    /**
     * Controlador, muestra la información de una película concreta
     */
    @GetMapping("/film")
    public String mostrarFilm(@RequestParam("id") Integer id, Model model, HttpSession session) {

        PeliculaDTO pelicula = peliculasService.buscarPeliculaDTO(id);

        if (pelicula == null) {
            model.addAttribute("mensaje", "La película solicitada no existe");
            return "error";
        }
        model.addAttribute("pelicula", pelicula);

        // Para saber si esta película está entre sus favoritas
        boolean peliculaFavorita = false;
        boolean peliculaVista = false;

        Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESION);

        if (usuario != null) {
            // Asegurarnos de tener el usuario actualizado desde la base de datos
            // Usar métodos DTO en lugar de entidades
            ListaDTO peliculasFavoritasDTO = listasService.getListaFavoritasDTO(usuario.getId());
            peliculaFavorita = peliculasFavoritasDTO != null
                    && peliculasFavoritasDTO.getPeliculasId().contains(pelicula.getId());

            ListaDTO peliculasVistasDTO = listasService.getListaVistasDTO(usuario.getId());
            peliculaVista = peliculasVistasDTO != null
                    && peliculasVistasDTO.getPeliculasId().contains(pelicula.getId());

            List<ListaDTO> listasUsuarioDTO = listasService.getListasDTOByUsuario(usuario.getId()); // Obtiene las
                                                                                                    // listas del
            // usuario (sin contar la de
            // fav y vistas)
            model.addAttribute("listasUsuario", listasUsuarioDTO);

            List<ListaDTO> listasPeliculaDTO = new ArrayList<>();
            for (ListaDTO l : listasUsuarioDTO) {
                if (l.getPeliculasId().contains(pelicula.getId())) {
                    listasPeliculaDTO.add(l);
                }
            }
            model.addAttribute("listasPelicula", listasPeliculaDTO);
        }

        model.addAttribute("peliculaFavorita", peliculaFavorita);
        model.addAttribute("peliculaVista", peliculaVista);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "film";
    }

    /**
     * El controlador recibe la acción de poner como favorita para el usuario la
     * película entregada
     * 
     * @param idPelicula El identificador de la película
     */
    @PostMapping("/favorite")
    public String doFavorite(@RequestParam("idPelicula") Integer idPelicula, HttpServletRequest request,
            HttpSession session) {

        // Un usuario no puede guardarse una películas como favorita si tiene la sesión
        // iniciada
        if (!estaAutenticado(request, session)) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);

        // Obtenemos la lista de favoritas como entidad (el servicio maneja internamente
        // la conversión)
        Lista favoritas = listasService.getListaFavoritas(usuario.getId());
        Pelicula pelicula = peliculasService.buscarPelicula(idPelicula);

        if (favoritas != null && favoritas.getPeliculas().contains(pelicula)) { // Ya era favorita y la quitamos
            favoritas.getPeliculas().remove(pelicula);
        } else if (favoritas != null) { // La introducimos como favorita
            favoritas.getPeliculas().add(pelicula);
        }

        // Guardamos los cambios y obtenemos el DTO
        if (favoritas != null) {
            this.listasService.guardarListaDTO(favoritas);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/film?id=" + idPelicula);
    }

    /**
     * El controlador recibe la acción de poner como vista para el usuario la
     * película entregada
     * 
     * @param idPelicula El identificador de la película
     */
    @PostMapping("/seen")
    public String doSeen(@RequestParam("idPelicula") Integer idPelicula, HttpServletRequest request,
            HttpSession session) {

        // Un usuario no puede guardarse una películas como vistas si tiene la sesión
        // iniciada
        if (!estaAutenticado(request, session)) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);

        // Usar el método específico para listas vistas
        Lista vistas = listasService.getListaVistas(usuario.getId());
        Pelicula pelicula = peliculasService.buscarPelicula(idPelicula);

        if (vistas != null && vistas.getPeliculas().contains(pelicula)) { // Ya estaba vista y la quitamos
            vistas.getPeliculas().remove(pelicula);
        } else if (vistas != null) { // La introducimos como vista
            vistas.getPeliculas().add(pelicula);
        }

        // Guardamos los cambios usando el método DTO
        if (vistas != null) {
            this.listasService.guardarListaDTO(vistas);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/film?id=" + idPelicula);
    }

    /**
     * El controlador recibe la acción de poner como favorita para el usuario la
     * película entregada
     * 
     * @param idPelicula El identificador de la película
     */
    @PostMapping("/addToList")
    public String doAddToList(@RequestParam("idPelicula") Integer idPelicula,
            @RequestParam(value = "listasSeleccionadas", required = false) List<Integer> listasSeleccionadas,
            HttpServletRequest request, HttpSession session) {

        if (listasSeleccionadas == null) {
            listasSeleccionadas = new ArrayList<>();
        }

        // Un usuario no puede guardarse una película a una lista si tiene la sesión
        // iniciada
        if (!estaAutenticado(request, session)) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);

        Pelicula pelicula = peliculasService.buscarPelicula(idPelicula);

        // Obtenemos las listas como entidades (en este caso es mejor trabajar con
        // entidades
        // ya que necesitamos modificar colecciones)
        List<Lista> listasUsuario = listasService.getListasByUsuario(usuario.getId());

        for (Lista l : listasUsuario) {
            if (listasSeleccionadas.contains(l.getId())) {
                l.getPeliculas().add(pelicula);
            } else {
                if (l.getPeliculas().contains(pelicula)) {
                    l.getPeliculas().remove(pelicula);
                }
            }
            // Usar el método DTO para guardar cambios
            listasService.guardarListaDTO(l);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/film?id=" + idPelicula);
    }

    @GetMapping("/peliculas")
    public String mostrarPeliculas(Model model) {
        List<PeliculaDTO> peliculas = peliculasService.buscarPeliculaDTOByPopularidad(PageRequest.of(0, 10));
        model.addAttribute("peliculas", peliculas);

        // Para que la búsqueda y el filtro funcione
        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "peliculas";
    }

    @GetMapping("/informacionPelicula")
    public String mostrarInformacionPelicula(@RequestParam("id") Integer id, Model model) {
        PeliculaDTO pelicula = peliculasService.buscarPeliculaDTO(id);
        model.addAttribute("pelicula", pelicula);

        return "informacionPelicula";
    }

    @GetMapping("/mostrarLista")
    public String mostrarLista(@RequestParam("listaId") Integer listaId, Model model, HttpServletRequest request,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESION);

        // Esto lo he añadido, puede que de fallo REVISAR
        if (usuario != null) {
            int idUsuario = usuario.getId();
            usuario = usuarioService.buscarUsuario(idUsuario);
            // opcional si quieres recargarlo desde DB
            model.addAttribute("usuario", usuario);
        }

        // Usar el método que devuelve DTO en lugar de entidad
        ListaDTO listaDTO = listasService.getListaDTOById(listaId);
        if (listaDTO == null) {
            return "redirect:/error"; // o una página 404
        }

        model.addAttribute("listaDTO", listaDTO);
        // Añadir el servicio de películas para poder obtener detalles de las películas
        model.addAttribute("peliculasService", peliculasService);

        // Verificar si la lista es favorita para el usuario
        boolean peliculaFavorita = false;
        if (usuario != null) {
            ListaDTO favoritasDTO = listasService.getListaFavoritasDTO(usuario.getId());
            peliculaFavorita = favoritasDTO != null && favoritasDTO.getPeliculasId().contains(listaDTO.getId());
            model.addAttribute("peliculaFavorita", peliculaFavorita);
        }

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "mostrarLista";
    }

}