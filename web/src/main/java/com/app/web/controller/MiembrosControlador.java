package com.app.web.controller;

import com.app.web.dto.ListaDTO;
import com.app.web.dto.UsuarioDTO;
import com.app.web.service.GenerosService;
import com.app.web.service.ListasService;
import com.app.web.service.MiembrosService;
import com.app.web.ui.FiltroBusquedaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.app.web.utils.Constantes.USUARIO_SESION;
import static com.app.web.utils.Constantes.USER_ADMIN;

@Controller
public class MiembrosControlador extends BaseControlador {

    @Autowired protected MiembrosService miembrosService;
    @Autowired protected ListasService listasService;
    @Autowired protected GenerosService generosService;

    /**
     * Controlador de la petición del sistema para cargar todos los miembros de
     * WikiMovies
     */
    @GetMapping("/miembros")
    public String mostrarMiembros(Model model) {

        // Cargamos los generos para el filtro de búsqueda de películas
        model.addAttribute("generos", generosService.getAllGeneros());
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("miembros", miembrosService.obtenerMiembros(null));
        return "miembros";
    }

    /**
     * Controlador de la petición del sistema para cargar todos los miembros de
     * WikiMovies que cumplan con un filtro
     */
    @GetMapping("/miembros/filtro")
    public String mostrarFiltro(@RequestParam("nombre") String filtroNombre,  Model model) {

        // Cargamos los generos para el filtro de búsqueda de películas
        model.addAttribute("generos", generosService.getAllGeneros());
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("miembros", miembrosService.obtenerMiembros(filtroNombre));

        return "miembros";
    }

    /**
     * Controlador de la petición del sistema al cargar el profile de un usuario
     * 
     * @param id Identificador del usuario del profile
     * @return JSP de envío
     */
    @GetMapping("/profile")
    public String mostrarProfile(@RequestParam("id") Integer id, Model model) {

        model.addAttribute("generos", generosService.getAllGeneros());
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        // Obtenemos al usuario
        UsuarioDTO usuarioProfile = miembrosService.obtenerUsuario(id);
        model.addAttribute("usuarioProfile", usuarioProfile);

        // Obtenemos las listas del usuario
        List<ListaDTO> listasDTO = listasService.getListasUsuario(id);
        model.addAttribute("listasDTO", listasDTO);

        // Obtenemos el número de personas que sigue el usuario
        Integer numseguidos = miembrosService.numSeguidosUsuario(id);
        model.addAttribute("numseguidos", numseguidos);

        return "profile";
    }

    /**
     * Controlador del formulario de actualización del Profile del Usuario
     * 
     * @param usuarioProfile Usuario del profile
     * @return JSP
     */
    @PostMapping("/profile/update")
    public String doUpdateProfile(@ModelAttribute() UsuarioDTO usuarioProfile,
            HttpServletRequest request, HttpSession session) {

        if (!estaAutenticado(request, session)) {
            return "redirect:/login";
        }

        // Comprobamos que el usuario tenga permisos para editar un perfil
        // IMPORTANTE: De USUARIo debe pasar a USUARIODTO
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);
        Integer idUsuarioProfile = usuarioProfile.getIdUsuario();


        if( !usuario.getIdUsuario().equals(idUsuarioProfile) ) {
            if( usuario.getRol() != USER_ADMIN ) {
                // Alguien sin permisos intenta ajustar una cuenta
                return "redirect:/profile?id=" + idUsuarioProfile;
            }
            // El administrador ajusta una cuenta que no es la suya
            // usuario = usuarioRepositorio.getReferenceById(idUsuarioProfile);
        }

        miembrosService.editarUsuario(usuarioProfile);

        return "redirect:/profile?id=" + idUsuarioProfile;
    }

    @GetMapping("/seguir")
    public String seguir(@RequestParam("id") Integer idUsuarioSeguir, HttpSession session,
            HttpServletRequest request) {

        Integer miId = ((UsuarioDTO) session.getAttribute("usuario")).getIdUsuario();
        miembrosService.seguirUsuario( miId ,idUsuarioSeguir);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/miembros");
    }

    @GetMapping("/dejarSeguir")
    public String dejarSeguir(@RequestParam("id") Integer idUsuario, HttpSession session,
            HttpServletRequest request) {

        Integer miId = ((UsuarioDTO) session.getAttribute("usuario")).getIdUsuario();
        miembrosService.dejarDeSeguirUsuario( miId ,idUsuario);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/miembros");
    }


    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam("id") Integer idUsuario){

        miembrosService.eliminarUsuario(idUsuario);

        return "redirect:/miembros";
    }

    @GetMapping("/cambioRol")
    public String cambioRol(@RequestParam("id") Integer idUsuario) {

        miembrosService.editarRolPremium(idUsuario);

        return "redirect:/profile?id=" + idUsuario;
    }

}
