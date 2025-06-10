package com.app.web.controller;

import com.app.web.dto.ListaDTO;
import com.app.web.dto.UsuarioDTO;
import com.app.web.entity.Lista;
import com.app.web.entity.Usuario;
import com.app.web.service.GenerosService;
import com.app.web.service.ListasService;
import com.app.web.service.PeliculasService;
import com.app.web.service.UsuarioService;
import com.app.web.ui.FiltroBusquedaDTO;
import com.app.web.ui.NuevaLista;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.app.web.utils.Constantes.USUARIO_SESION;

/**
 * Controlador para operaciones relacionadas con listas
 */
@Controller
public class ListasControlador extends BaseControlador {

    @Autowired protected GenerosService generosService;
    @Autowired protected PeliculasService peliculasService;
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

        List<ListaDTO> listas = listasService.getAllListasDTO();
        model.addAttribute("listas", listas);

        loadCommonModelAttributes(model);

        return "listasPopulares";
    }

    /**
     * Controlador para crear una nueva lista la cual comienza vacía
     */
    @GetMapping("/crearLista")
    public String crearLista(Model model, HttpServletRequest request, HttpSession session) {
        
        // El usuario debe estar conectado para crear una lista
        if (!estaAutenticado(request, session)) {
            return "redirect:/login";
        }

        loadCommonModelAttributes(model);
        model.addAttribute("nuevaLista", new NuevaLista());

        return "crearLista";
    }

    /**
     * Controlador para mostrar listas de usuarios seguidos
     */
    @GetMapping("/listasSeguidos")
    public String listasSeguidos(@RequestParam("id") Integer id, Model model, HttpSession session) {
        
        // Obtenemos los usuarios que sigue el usuario
        List<Usuario> seguidos = usuarioService.usuarioSeguidos(id);
        List<ListaDTO> listasSeguidos = new ArrayList<>();

        // Recolectar listas de usuarios seguidos
        for (Usuario seguido : seguidos) {
            if (seguido.getListas() != null) {
                // Convertir entidades Lista a ListaDTO
                listasSeguidos.addAll(seguido.getListas().stream()
                        .map(Lista::toDTO)
                        .collect(Collectors.toList()));
            }
        }
        model.addAttribute("listasSeguidos", listasSeguidos);

        loadCommonModelAttributes(model);

        return "listasSeguidos";
    }

    /**
     * Controlador para mostrar las listas propias del usuario
     */
    @GetMapping("/misListas")
    public String misListas(@RequestParam("id") Integer id, Model model) {

        Usuario miUsuario = usuarioService.buscarUsuario(id);
        Set<Lista> misListasEntidades = miUsuario.getListas();

        // Convertir entidades Lista a ListaDTO
        Set<ListaDTO> misListas = misListasEntidades.stream()
                .map(Lista::toDTO)
                .collect(Collectors.toSet());

        model.addAttribute("misListas", misListas);

        loadCommonModelAttributes(model);

        return "misListas";
    }

    /**
     * Guarda en la base de datos la nueva lista, creada por el usuario
     */
    @PostMapping("/guardarLista")
    public String guardarLista(@ModelAttribute("nuevaLista") NuevaLista nuevaLista, HttpSession session) {
        
        // Obtenemos el usuario 
        int idUsuario = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);

        Lista lista = new Lista();
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

        // Crear lista vacía - ya no añadimos películas aquí
        lista.setPeliculas(new HashSet<>()); // Lista vacía

        // Guardar la lista usando el servicio
        ListaDTO listaGuardada = listasService.guardarListaDTO(lista);

        // Redirigir a la vista de la lista creada para que puedan añadir películas
        return "redirect:/mostrarLista?listaId=" + listaGuardada.getId();
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

    /**
     * Controlador para mostrar el formulario de edición de lista
     */
    @PostMapping("/editarLista")
    public String editarLista(@RequestParam("listaId") Integer listaId, Model model) {
        
        // Obtenemos el DTO de la entidad lista
        ListaDTO listaDTO = listasService.getListaDTOById(listaId);

        if (listaDTO == null) {
            return "redirect:/listas";
        }

        // Preparar datos para el formulario de edición
        NuevaLista editarLista = new NuevaLista();
        editarLista.setNombre(listaDTO.getNombre());
        editarLista.setDescripcion(listaDTO.getDescripcion());
        editarLista.setListaId(listaDTO.getId());

        loadCommonModelAttributes(model);
        model.addAttribute("editarLista", editarLista);
        model.addAttribute("lista", listaDTO);

        return "editarLista";
    }

    /**
     * Controlador para guardar cambios en una lista editada
     */
    @PostMapping("/guardarCambiosLista")
    public String guardarCambiosLista(@ModelAttribute("editarLista") NuevaLista editarLista) {
        
        // Obtenemos la entidad Lista para actualizarla
        Lista lista = listasService.getListaById(editarLista.getListaId());

        if (lista == null) {
            return "redirect:/listas";
        }

        // Actualizar propiedades de la lista
        lista.setNombre(editarLista.getNombre());
        lista.setDescripcion(editarLista.getDescripcion());

        // Actualizar URL de imagen si se proporciona
        if (!(editarLista.getFotoUrl() == null || editarLista.getFotoUrl().isEmpty())) {
            lista.setImgURL(editarLista.getFotoUrl());
        }

        // Guardar cambios usando el servicio (devuelve DTO)
        listasService.guardarListaDTO(lista);

        return "redirect:/mostrarLista?listaId=" + editarLista.getListaId();
    }
}
