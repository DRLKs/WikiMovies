package com.app.web.controller;

import com.app.web.dto.ListaDTO;
import com.app.web.dto.UsuarioDTO;
import com.app.web.entity.Lista;
import com.app.web.entity.Usuario;
import com.app.web.service.GenerosService;
import com.app.web.service.ListasService;
import com.app.web.service.UsuarioService;
import com.app.web.ui.FiltroBusquedaDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.app.web.utils.Constantes.USUARIO_SESION;

/**
 * Controlador para operaciones relacionadas con listas
 */
@Controller
public class ListasControlador extends BaseControlador {

    @Autowired protected GenerosService generosService;
    @Autowired protected UsuarioService usuarioService;
    @Autowired protected ListasService listasService;

    /**
     * Carga los atributos comunes del modelo necesarios para todas las vistas de
     * listas
     */
    private void loadCommonModelAttributes(Model model) {
        // Añadir filtro de búsqueda
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        // Añadir géneros para filtros
        model.addAttribute("generos", generosService.getAllGeneros());
    }

    /**
     * Controlador para mostrar todas las listas
     */
    @GetMapping("/listas")
    public String mostrarListas(Model model) {

        List<ListaDTO> listas = listasService.getListasPopularesDTO();
        model.addAttribute("listas", listas);

        model.addAttribute("apartado", "populares");

        loadCommonModelAttributes(model);

        return "listas";
    }

    /**
     * Controlador para mostrar listas de usuarios seguidos
     */
    @GetMapping("/listasSeguidos")
    public String listasSeguidos(@RequestParam("id") Integer id, Model model) {
        
        // Obtenemos los usuarios que sigue el usuario
        List<UsuarioDTO> seguidos = usuarioService.usuarioSeguidos(id);
        List<ListaDTO> listasSeguidos = new ArrayList<>();

        // Recolectar listas de usuarios seguidos
        for (UsuarioDTO seguido : seguidos) {
            listasSeguidos.addAll( listasService.getListasUsuarioNoFavoritasNoVistas( seguido.getIdUsuario() ) );
        }
        model.addAttribute("listas", listasSeguidos);

        model.addAttribute("apartado", "seguidos");

        loadCommonModelAttributes(model);

        return "listas";
    }

    /**
     * Controlador para mostrar las listas propias del usuario
     */
    @GetMapping("/misListas")
    public String misListas(@RequestParam("id") Integer id, Model model) {


        // Convertir entidades Lista a ListaDTO y la lista a tipo List
        List<ListaDTO> misListas = listasService.getListasUsuario(id);

        model.addAttribute("listas", misListas);

        model.addAttribute("apartado", "misListas");

        loadCommonModelAttributes(model);

        return "listas";
    }

    /**
     * Controlador para eliminar una película de una lista
     */
    @GetMapping("/quitarPeliLista")
    public String quitarPeliLista(
            @RequestParam("idPeli") Integer idPeli,
            @RequestParam("idLista") Integer idLista) {

        // Usar el servicio para eliminar la película de la lista (devuelve DTO)
        listasService.removePeliculaFromListaDTO(idLista, idPeli);

        return "redirect:/mostrarLista?listaId=" + idLista;
    }

    @GetMapping("/mostrarLista")
    public String mostrarLista(@RequestParam("listaId") Integer listaId, Model model) {

        // Usar el método que devuelve DTO en lugar de entidad
        ListaDTO listaDTO = listasService.getListaDTOById(listaId);
        if (listaDTO == null) {
            return "redirect:/error"; // o una página 404
        }

        model.addAttribute("listaDTO", listaDTO);

        model.addAttribute("generos", generosService.getAllGeneros());

        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        return "mostrarLista";
    }

    /**
     * Controlador para crear una nueva lista la cual comienza vacía
     */
    @GetMapping("/crearLista")
    public String crearLista(Model model, HttpSession session) {

        // El usuario debe estar conectado para crear una lista
        if (!estaAutenticado(session)) {
            return "redirect:/login";
        }

        loadCommonModelAttributes(model);
        ListaDTO nuevaLista = new ListaDTO();
        nuevaLista.setId(-1);
        model.addAttribute("lista", nuevaLista);

        return "editarLista";
    }

    /**
     * Controlador para mostrar el formulario de edición de lista
     */
    @PostMapping("/editarLista")
    public String editarLista(@RequestParam("listaId") Integer listaId, Model model) {
        
        // Obtenemos el DTO de la entidad lista
        ListaDTO lista = listasService.getListaDTOById(listaId);

        if (lista == null) {
            return "redirect:/listas";
        }

        loadCommonModelAttributes(model);
        model.addAttribute("lista", lista);

        return "editarLista";
    }

    /**
     * Guarda en la base de datos la nueva lista, creada por el usuario
     */
    @PostMapping("/guardarLista")
    public String guardarLista(@ModelAttribute("model_lista") ListaDTO nuevaLista, HttpSession session) {

        // Obtenemos el usuario
        int idUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);
        Lista lista = listasService.getListaById(nuevaLista.getId());
        if(lista == null) { // Estamos creando la lista
            lista = new Lista();
            lista.setPeliculas(new HashSet<>());
        }

        lista.setNombre(nuevaLista.getNombre());
        lista.setDescripcion(nuevaLista.getDescripcion());

        // Establecer imagen predeterminada si no se proporciona
        if (nuevaLista.getFotoUrl() == null || nuevaLista.getFotoUrl().isEmpty()) {
            lista.setImgURL(
                    "https://media.istockphoto.com/id/93986448/es/foto/clapper.jpg?s=612x612&w=0&k=20&c=bbZguzBvEVkMczAm-NCcYKR8FLpsJvfogOEr9J_5WnA=");
        } else {
            lista.setImgURL(nuevaLista.getFotoUrl());
        }

        lista.setIdUsuario(usuario);

        // Guardar la lista usando el servicio
        ListaDTO listaGuardada = listasService.guardarListaDTO(lista);

        // Redirigir a la vista de la lista creada para que puedan añadir películas
        return "redirect:/mostrarLista?listaId=" + listaGuardada.getId();
    }

    /**
     * Controlador para eliminar una lista
     */
    @PostMapping("/eliminarLista")
    public String eliminarLista(@RequestParam("listaId") Integer listaId, HttpSession session) {
        listasService.eliminarLista(listaId);
        Integer idUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();
        return "redirect:/misListas?id=" + idUsuario;
    }
}
