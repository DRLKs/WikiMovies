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

        // Obtenemos la lista de favoritas como DTO
        ListaDTO favoritasDTO = listasService.getListaFavoritasDTO(idUsuario);

        if (favoritasDTO != null) {
            // Comprobar si la película ya está en favoritos
            if (favoritasDTO.getPeliculasId().contains(idPelicula)) {
                // Ya era favorita y la quitamos
                favoritasDTO.getPeliculasId().remove(idPelicula);
            } else {
                // La introducimos como favorita
                favoritasDTO.getPeliculasId().add(idPelicula);
            }

            // Guardamos los cambios usando el DTO
            this.listasService.guardarListaDTO(favoritasDTO);
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

        // Usar el método que devuelve DTO para listas vistas
        ListaDTO vistasDTO = listasService.getListaVistasDTO(idUsuario);

        if (vistasDTO != null) {
            // Comprobar si la película ya está en vistas
            if (vistasDTO.getPeliculasId().contains(idPelicula)) {
                // Ya estaba vista y la quitamos
                vistasDTO.getPeliculasId().remove(idPelicula);
            } else {
                // La introducimos como vista
                vistasDTO.getPeliculasId().add(idPelicula);
            }

            // Guardamos los cambios usando el DTO
            this.listasService.guardarListaDTO(vistasDTO);
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
        } // Obtenemos los datos del usuario
        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();

        // Obtenemos todas las listas como DTOs
        List<ListaDTO> listasUsuarioDTO = listasService.getListasDTOByUsuario(idUsuario);

        for (ListaDTO lista : listasUsuarioDTO) {
            if (listasSeleccionadas.contains(lista.getId())) {
                // Si la lista está seleccionada, añadimos la película si no está ya
                if (!lista.getPeliculasId().contains(idPelicula)) {
                    lista.getPeliculasId().add(idPelicula);
                }
            } else {
                // Si la lista no está seleccionada, quitamos la película si está
                lista.getPeliculasId().remove(idPelicula);
            }
            // Guardamos los cambios usando el método DTO
            listasService.guardarListaDTO(lista);
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