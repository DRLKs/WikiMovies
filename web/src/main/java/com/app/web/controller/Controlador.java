package com.app.web.controller;

import com.app.web.dto.*;
import com.app.web.service.*;
import com.app.web.ui.FiltroBusquedaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

import static com.app.web.utils.Constantes.*;

@Controller
public class Controlador extends BaseControlador {

    @Autowired protected PeliculasService peliculasService;
    @Autowired protected GenerosService generosService;
    @Autowired private IdiomasService idiomasService;
    @Autowired protected ListasService listasService;
    /**
     * Controlador de la pantalla inicial
     */
    @GetMapping("/")
    public String index(Model model, HttpSession session) {

        if (estaAutenticado(session)) {
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);
            ListaDTO favoritas = listasService.getListaFavoritasDTO(usuario.getIdUsuario());
            ListaDTO vistas = listasService.getListaVistasDTO(usuario.getIdUsuario());
            // Realizamos lo del recomendador de las películas Favoritas aquí
            if (usuario.getRol() > USER_NORMAL && !favoritas.getPeliculas().isEmpty()) {
                List<GeneroDTO> generosFavoritas = listasService.generosMasRepetidosEnLaLista(favoritas);
                List<PeliculaDTO> peliculasRecomendadasFavoritas = new ArrayList<>();
                if (generosFavoritas.size() > 1) {
                    peliculasRecomendadasFavoritas = peliculasService.filtrarPorDosGeneros(favoritas.getPeliculas(), generosFavoritas.get(0), generosFavoritas.get(1));
                } else if (generosFavoritas.size() == 1) {
                    peliculasRecomendadasFavoritas = peliculasService.filtrarPorDosGeneros(favoritas.getPeliculas(), generosFavoritas.get(0), null);
                }
                model.addAttribute("peliculasRecomendadasFavoritas", peliculasRecomendadasFavoritas);
            }

            // Realizamos lo del recomendador de las películas Vistas aquí
            if (usuario.getRol() > USER_NORMAL && !vistas.getPeliculas().isEmpty()) {
                List<GeneroDTO> generosVistas = listasService.generosMasRepetidosEnLaLista(vistas);
                List<PeliculaDTO> peliculasRecomendadasVistas = new ArrayList<>();
                if (generosVistas.size() > 1) {
                    peliculasRecomendadasVistas = peliculasService.filtrarPorDosGeneros(vistas.getPeliculas(), generosVistas.get(0), generosVistas.get(1));
                } else if (generosVistas.size() == 1) {
                    peliculasRecomendadasVistas = peliculasService.filtrarPorDosGeneros(vistas.getPeliculas(), generosVistas.get(0), null);
                }
                model.addAttribute("peliculasRecomendadasVistas", peliculasRecomendadasVistas);
            }
        }

        List<PeliculaDTO> peliculas = peliculasService.getAllPeliculasDTO();
        model.addAttribute("peliculas", peliculas);


        List<PeliculaDTO> peliculasRanking = peliculasService.buscarPeliculaDTOByPopularidad(PageRequest.of(0, 3)); // La selección del día es un top 3 películas ordenadas por popularidad
        model.addAttribute("peliculasRanking", peliculasRanking);

        // Modelos necesarios para la barra de navegación
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("generos", generosService.getAllGeneros());

        return "index";
    }

    /**
     * Controlador del formulario sobre el filtro al buscar películas
     */
    @GetMapping("/search")
    public String search(@ModelAttribute() FiltroBusquedaDTO filtroBusquedaDTO, Model model, HttpSession session) {

        if (estaAutenticado(session)) {
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);

            model.addAttribute("favoritas", listasService.getListaFavoritasDTO(usuario.getIdUsuario()));
            model.addAttribute("vistas", listasService.getListaVistasDTO(usuario.getIdUsuario()));
            model.addAttribute("listasUsuario", listasService.getListasDTOByUsuario(usuario.getIdUsuario()));
        }

        List<PeliculaDTO> peliculas;
        Integer[] listaGeneros = filtroBusquedaDTO.getGeneros();
        String titulo = filtroBusquedaDTO.getTitulo();

        if (listaGeneros != null && listaGeneros.length > 0 && (titulo == null || titulo.trim().isEmpty())) { // Solo géneros
            peliculas = peliculasService.getAllPeliculasDTOByTituloYGenero("", listaGeneros);
            // Construir el título con los nombres de los géneros
            List<String> nombresGeneros = new ArrayList<>();
            for (Integer idGenero : listaGeneros) {
                GeneroDTO genero = generosService.getGenero(idGenero);
                if (genero != null) {
                    nombresGeneros.add(genero.getNombre());
                }
            }
            String tituloGeneros = String.join(", ", nombresGeneros);
            model.addAttribute("titulo", tituloGeneros);
        } else if (listaGeneros != null && listaGeneros.length > 0) { // Filtro de géneros y título
            peliculas = peliculasService.getAllPeliculasDTOByTituloYGenero(titulo, listaGeneros);
            model.addAttribute("titulo", titulo);
        } else {
            peliculas = peliculasService.getAllPeliculasDTOByTitulo(titulo);
            model.addAttribute("titulo", titulo);
        }

        model.addAttribute("peliculas", peliculas);

        // Modelos necesarios para la barra de navegación
        model.addAttribute("filtroBusquedaDTO", filtroBusquedaDTO);
        model.addAttribute("generos", generosService.getAllGeneros());

        return "peliculas";
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

        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);

        if (usuario != null) {
            // Asegurarnos de tener el usuario actualizado desde la base de datos
            // Usar métodos DTO en lugar de entidades
            ListaDTO peliculasFavoritasDTO = listasService.getListaFavoritasDTO(usuario.getIdUsuario());
            peliculaFavorita = peliculasFavoritasDTO != null
                    && peliculasFavoritasDTO.getPeliculas().contains(pelicula);

            ListaDTO peliculasVistasDTO = listasService.getListaVistasDTO(usuario.getIdUsuario());
            peliculaVista = peliculasVistasDTO != null
                    && peliculasVistasDTO.getPeliculas().contains(pelicula);

            List<ListaDTO> listasUsuarioDTO = listasService.getListasDTOByUsuario(usuario.getIdUsuario()); // Obtiene las
            // listas del
            // usuario (sin contar la de
            // fav y vistas)
            model.addAttribute("listasUsuario", listasUsuarioDTO);

            List<ListaDTO> listasPeliculaDTO = new ArrayList<>();
            for (ListaDTO l : listasUsuarioDTO) {
                if (l.getPeliculas().contains(pelicula)) {
                    listasPeliculaDTO.add(l);
                }
            }
            model.addAttribute("listasPelicula", listasPeliculaDTO);
        }

        model.addAttribute("peliculaFavorita", peliculaFavorita);
        model.addAttribute("peliculaVista", peliculaVista);

        // Modelos necesarios para la barra de navegación
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("generos", generosService.getAllGeneros());

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

        // Un usuario solo puede guardarse una película como favorita si tiene la sesión iniciada
        if (!estaAutenticado(session)) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();

        listasService.peliculaAccionFavorita(idPelicula, idUsuario);

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

        // Un usuario no puede guardarse una películas como vistas si no tiene la sesión iniciada
        if (!estaAutenticado(session)) {
            return "redirect:/login";
        }

        // Obtenemos los datos del usuario
        int idUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();

        listasService.peliculaAccionVer(idPelicula,idUsuario);

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
        if (!estaAutenticado(session)) {
            return "redirect:/login";
        } // Obtenemos los datos del usuario
        int idUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();

        // Obtenemos todas las listas como DTOs
        List<ListaDTO> listasUsuarioDTO = listasService.getListasDTOByUsuario(idUsuario);
        PeliculaDTO pelicula = peliculasService.getPeliculaDTOById(idPelicula);
        for (ListaDTO lista : listasUsuarioDTO) {
            if (listasSeleccionadas.contains(lista.getId())) {
                // Si la lista está seleccionada, añadimos la película si no está ya
                if (!lista.getPeliculas().contains(pelicula)) {
                    lista.getPeliculas().add(pelicula);
                }
            } else {
                // Si la lista no está seleccionada, quitamos la película si está
                lista.getPeliculas().remove(pelicula);
            }
            // Guardamos los cambios usando el método DTO
            listasService.guardarListaDTO(lista);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/film?id=" + idPelicula);
    }

    @GetMapping("/peliculas")
    public String mostrarPeliculas(Model model, HttpSession session) {
        List<PeliculaDTO> peliculas = peliculasService.getAllPeliculasDTO();
        model.addAttribute("peliculas", peliculas);

        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);

        // Solo cargar las listas de favoritas y vistas si el usuario está autenticado
        if (usuario != null) {
            model.addAttribute("favoritas", listasService.getListaFavoritasDTO(usuario.getIdUsuario()));
            model.addAttribute("vistas", listasService.getListaVistasDTO(usuario.getIdUsuario()));
        } else {
            // Para usuarios no autenticados, pasar null o listas vacías
            model.addAttribute("favoritas", null);
            model.addAttribute("vistas", null);
        }

        // Para que la búsqueda y el filtro funcione
        model.addAttribute("titulo", "");

        // Modelos necesarios para la barra de navegación
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("generos", generosService.getAllGeneros());

        return "peliculas";
    }

    @GetMapping("/informacionPelicula")
    public String mostrarInformacionPelicula(@RequestParam("id") Integer id, Model model) {
        PeliculaDTO pelicula = peliculasService.buscarPeliculaDTO(id);
        model.addAttribute("pelicula", pelicula);

        return "informacionPelicula";
    }

    @PostMapping("/editarPelicula")
    public String editarPelicula(@RequestParam("idPelicula") Integer idPelicula, Model model) {
        PeliculaDTO pelicula = peliculasService.buscarPeliculaDTO(idPelicula);
        model.addAttribute("pelicula", pelicula);

        model.addAttribute("idiomas", idiomasService.getAllIdiomas());

        // Modelos necesarios para la barra de navegación
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("generos", generosService.getAllGeneros());

        return "editarPelicula";
    }

    @GetMapping("/crearPelicula")
    public String crearPelicula(Model model,  HttpSession session) {
        if (!estaAutenticado(session)) {
            return "redirect:/login";
        }
        int rolUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getRol();
        if(rolUsuario != 2) {
            return"redirect:/";
        }

        model.addAttribute("pelicula", null);

        model.addAttribute("idiomas", idiomasService.getAllIdiomas());

        // Modelos necesarios para la barra de navegación
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("generos", generosService.getAllGeneros());

        return "editarPelicula";
    }

    @PostMapping("/guardarPelicula")
    public String guardarPelicula(@RequestParam("idPelicula") Integer idPelicula,
            @RequestParam("titulo") String titulo,
            @RequestParam("fecha_estreno") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_estreno,
            @RequestParam("presupuesto") Integer presupuesto,
            @RequestParam("ingresos") Integer ingresos,
            @RequestParam("duracion") Integer duracion,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("enlace") String enlace,
            @RequestParam("idioma_original") Integer idioma_original,
            @RequestParam("estatus") String estatus,
            @RequestParam("eslogan") String eslogan,
            @RequestParam("poster") String poster,
            @RequestParam("generos") List<Integer> idGeneros) {

        PeliculaDTO peliculaDTO;
        if (idPelicula == -1) { // Queremos crear una pelicula
            peliculaDTO = new PeliculaDTO();
        } else { // Queremos editar una pelicula
            peliculaDTO = peliculasService.buscarPeliculaDTO(idPelicula);
        }

        peliculaDTO.setTitulo(titulo);
        peliculaDTO.setTitulooriginal(titulo);
        peliculaDTO.setFechaEstreno(fecha_estreno);
        peliculaDTO.setPresupuesto(presupuesto.longValue());
        peliculaDTO.setIngresos(ingresos.longValue());
        peliculaDTO.setDuracion(duracion);
        peliculaDTO.setDescripcion(descripcion);
        peliculaDTO.setEnlace(enlace);
        IdiomaDTO idioma = idiomasService.getIdiomaById(idioma_original);
        peliculaDTO.setIdiomaOriginal(idioma);
        peliculaDTO.setEstatus(estatus);
        peliculaDTO.setEslogan(eslogan);
        peliculaDTO.setPoster(poster);
        Set<GeneroDTO> generos = new HashSet<>();
        for (Integer id : idGeneros) {
            GeneroDTO genero = generosService.getGenero(id);
            generos.add(genero);
        }
        peliculaDTO.setGeneros(generos);

        // Asignar valores aleatorios a número de votos, media de votos y popularidad
        if(peliculaDTO.getPopularidad() == null && peliculaDTO.getMediaVotos() == null && peliculaDTO.getNumeroVotos() == null) {
            Random random = new Random();
            int numeroVotos = 1000 + random.nextInt(9000); // entre 1000 y 10000
            float mediaVotos = 6.0f + random.nextFloat() * 3.5f; // entre 6.0 y 9.5
            float variacion = (random.nextFloat() - 0.5f); // entre -0.5 y +0.5
            float popularidad = mediaVotos + variacion;

            peliculaDTO.setNumeroVotos(numeroVotos);
            peliculaDTO.setMediaVotos((float) Math.round(mediaVotos * 10) / 10); // redondeo a 1 decimal
            peliculaDTO.setPopularidad((float) Math.round(popularidad * 10) / 10); // redondeo a 1 decimal
        }

        PeliculaDTO guardada = peliculasService.guardarPeliculaDTO(peliculaDTO);
        return "redirect:/film?id=" + guardada.getId();
    }

    @PostMapping("/eliminarPelicula")
    public String eliminarPelicula(@RequestParam("idPelicula") Integer idPelicula) {
        peliculasService.eliminarPelicula(idPelicula);
        return "redirect:/peliculas";
    }

}