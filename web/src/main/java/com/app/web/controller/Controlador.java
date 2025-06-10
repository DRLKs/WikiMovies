package com.app.web.controller;

import com.app.web.dao.GenerosRepository;
import com.app.web.dao.IdiomasRepository;
import com.app.web.dto.GeneroDTO;
import com.app.web.dto.ListaDTO;
import com.app.web.dto.PeliculaDTO;
import com.app.web.dto.UsuarioDTO;
import com.app.web.entity.Idioma;
import com.app.web.entity.Genero;
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

    @Autowired PeliculasService peliculasService;
    @Autowired GenerosService generosService;

    @Autowired protected ListasService listasService;

    @Autowired IdiomasRepository idiomasRepository;
    @Autowired GenerosRepository generosRepositorio;
    /**
     * Controlador de la pantalla inicial
     */
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request,
                        HttpSession session) {

        if (estaAutenticado(request, session)) {
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);
            ListaDTO favoritas = listasService.getListaFavoritasDTO(usuario.getIdUsuario());
            // Realizamos lo del recomendador aquí
            if(usuario.getRol()>0 && favoritas.getPeliculasId().size()>0) {
                List<GeneroDTO> generosFavoritas = listasService.generosMasRepetidosEnLaLista(favoritas);
                List<PeliculaDTO> peliculasRecomendadasFavoritas;
                if(generosFavoritas.size()>1) {
                    peliculasRecomendadasFavoritas = peliculasService.filtrarPorDosGeneros(favoritas.getPeliculasId(), generosFavoritas.get(0), generosFavoritas.get(1));
                } else {
                    peliculasRecomendadasFavoritas = peliculasService.filtrarPorDosGeneros(favoritas.getPeliculasId(), generosFavoritas.get(0), null);
                }
                model.addAttribute("peliculasRecomendadasFavoritas", peliculasRecomendadasFavoritas);
            }
        } else {
            model.addAttribute("peliculasRecomendadasFavoritas", new ArrayList<>());
        }

        List<PeliculaDTO> peliculas = peliculasService.getAllPeliculasDTO();
        model.addAttribute("peliculas", peliculas);

        model.addAttribute("generos", generosService.getAllGeneros());

        List<PeliculaDTO> peliculasRanking = peliculasService.buscarPeliculaDTOByPopularidad(PageRequest.of(0, 10));
        model.addAttribute("peliculasRanking", peliculasRanking);

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
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);

            model.addAttribute("favoritas", listasService.getListaFavoritasDTO(usuario.getIdUsuario()));
            model.addAttribute("vistas", listasService.getListaVistasDTO(usuario.getIdUsuario()));
            model.addAttribute("listasUsuario", listasService.getListasDTOByUsuario(usuario.getIdUsuario()));
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

        model.addAttribute("generos", generosService.getAllGeneros());

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

        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);

        if (usuario != null) {
            // Asegurarnos de tener el usuario actualizado desde la base de datos
            // Usar métodos DTO en lugar de entidades
            ListaDTO peliculasFavoritasDTO = listasService.getListaFavoritasDTO(usuario.getIdUsuario());
            peliculaFavorita = peliculasFavoritasDTO != null
                    && peliculasFavoritasDTO.getPeliculasId().contains(pelicula.getId());

            ListaDTO peliculasVistasDTO = listasService.getListaVistasDTO(usuario.getIdUsuario());
            peliculaVista = peliculasVistasDTO != null
                    && peliculasVistasDTO.getPeliculasId().contains(pelicula.getId());

            List<ListaDTO> listasUsuarioDTO = listasService.getListasDTOByUsuario(usuario.getIdUsuario()); // Obtiene las
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

        model.addAttribute("generos", generosService.getAllGeneros());

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

        // Un usuario no puede guardarse una película como favorita si tiene la sesión
        // iniciada
        if (!estaAutenticado(request, session)) {
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
        if (!estaAutenticado(request, session)) {
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
        if (!estaAutenticado(request, session)) {
            return "redirect:/login";
        } // Obtenemos los datos del usuario
        int idUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();

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
    public String mostrarPeliculas(Model model, HttpSession session) {
        List<PeliculaDTO> peliculas = peliculasService.buscarPeliculaDTOByPopularidad(PageRequest.of(0, 10));
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
        model.addAttribute("generos", generosService.getAllGeneros());

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

        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);

        // Usar el método que devuelve DTO en lugar de entidad
        ListaDTO listaDTO = listasService.getListaDTOById(listaId);
        if (listaDTO == null) {
            return "redirect:/error"; // o una página 404
        }

        model.addAttribute("listaDTO", listaDTO);
        // Añadir el servicio de películas para poder obtener detalles de las películas
        model.addAttribute("peliculasService", peliculasService);

        // Verificar si la lista es favorita para el usuario
        boolean peliculaFavorita;
        if (usuario != null) {
            ListaDTO favoritasDTO = listasService.getListaFavoritasDTO(usuario.getIdUsuario());
            peliculaFavorita = favoritasDTO != null && favoritasDTO.getPeliculasId().contains(listaDTO.getId());
            model.addAttribute("peliculaFavorita", peliculaFavorita);
        }

        model.addAttribute("generos", generosService.getAllGeneros());

        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "mostrarLista";
    }

    @PostMapping("/editarPelicula")
    public String editarPelicula(@RequestParam("idPelicula") Integer idPelicula, Model model) {
        PeliculaDTO pelicula = peliculasService.buscarPeliculaDTO(idPelicula);
        model.addAttribute("pelicula", pelicula);

        model.addAttribute("generos", generosService.getAllGeneros());

        List<Idioma> idiomas = idiomasRepository.findAll();
        model.addAttribute("idiomas", idiomas);

        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "editarPelicula";
    }

    @GetMapping("/crearPelicula")
    public String crearPelicula(Model model) {

        model.addAttribute("pelicula", null);

        model.addAttribute("generos", generosService.getAllGeneros());

        List<Idioma> idiomas = idiomasRepository.findAll();
        model.addAttribute("idiomas", idiomas);

        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

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
            @RequestParam("generos") List<Integer> idGeneros,
            Model model, HttpServletRequest request) {
        PeliculaDTO peliculaDTO = null;
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
        Idioma idioma = idiomasRepository.findById(idioma_original).orElse(null);
        peliculaDTO.setIdiomaOriginal(idioma);
        peliculaDTO.setEstatus(estatus);
        peliculaDTO.setEslogan(eslogan);
        peliculaDTO.setPoster(poster);
        Set<Genero> generos = new HashSet<>();
        for (Integer id : idGeneros) {
            Genero genero = generosRepositorio.findById(id).orElse(null);
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