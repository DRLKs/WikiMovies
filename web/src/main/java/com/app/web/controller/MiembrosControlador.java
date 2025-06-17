package com.app.web.controller;

import com.app.web.dto.ListaDTO;
import com.app.web.dto.UsuarioDTO;
import com.app.web.service.*;
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

    @Autowired private MiembrosService miembrosService;
    @Autowired private ListasService listasService;
    @Autowired private GenerosService generosService;
    @Autowired private GenerosUsuariosService generosUsuariosService;
    @Autowired private RolesService rolesService;
    @Autowired private LoginService loginService;

    /**
     * Cargamos los modelos necesarios para la barra de navegación
     */
    private void loadCommonModelAttributes(Model model) {
        // Añadir filtro de búsqueda
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        // Añadir géneros para filtros
        model.addAttribute("generos", generosService.getAllGeneros());
    }

    /**
     * Controlador de la petición del sistema para cargar todos los miembros de
     * WikiMovies
     */
    @GetMapping("/miembros")
    public String mostrarMiembros(Model model) {

        // Cargamos los generos para el filtro de búsqueda de películas
        model.addAttribute("miembros", miembrosService.obtenerMiembros(null));
        model.addAttribute("filtro", null);

        loadCommonModelAttributes(model);

        return "miembros";
    }

    /**
     * Controlador de la petición del sistema para cargar todos los miembros de
     * WikiMovies que cumplan con un filtro
     */
    @GetMapping("/miembros/filtro")
    public String mostrarFiltro(@RequestParam("nombre") String filtroNombre,  Model model) {

        model.addAttribute("miembros", miembrosService.obtenerMiembros(filtroNombre));
        model.addAttribute("filtro", filtroNombre);

        loadCommonModelAttributes(model);

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

        // Obtenemos al usuario
        UsuarioDTO usuarioProfile = miembrosService.obtenerUsuario(id);
        if( usuarioProfile == null ) return "redirect:/miembros";
        model.addAttribute("usuarioProfile", usuarioProfile);

        // Obtenemos las listas del usuario, incluyendo Vistas + Favoritas
        List<ListaDTO> listasDTO = listasService.getListasUsuario(id);
        model.addAttribute("listasDTO", listasDTO);

        // Obtenemos el número de personas que sigue el usuario (El número de seguidores lo obtenemos directamente en el JSP)
        Integer numseguidos = miembrosService.numSeguidosUsuario(id);
        model.addAttribute("numseguidos", numseguidos);

        loadCommonModelAttributes(model);

        generosUsuariosService.cargarModelosGenerosUsuarios(model);
        rolesService.cargarModelosRoles(model);

        return "profile";
    }

    /**
     * Controlador del formulario de actualización del Profile del Usuario
     * 
     * @param usuarioProfile Usuario del profile
     * @return JSP
     */
    @PostMapping("/profile/update")
    public String doUpdateProfile(@ModelAttribute() UsuarioDTO usuarioProfile, HttpSession session) {

        if (!estaAutenticado(session)) {
            return "redirect:/login";
        }

        // Comprobamos que el usuario tenga permisos para editar un perfil
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute(USUARIO_SESION);
        Integer idUsuarioProfile = usuarioProfile.getIdUsuario();

        if( !usuario.getIdUsuario().equals(idUsuarioProfile) ) {    // Usuario logueado desigual a usuario del perfil
            if( usuario.getRol() != USER_ADMIN ) {                  // Usuario sin roles para editar perfiles
                // Alguien sin permisos intenta ajustar una cuenta
                return "redirect:/profile?id=" + idUsuarioProfile;
            }
        }

        miembrosService.editarUsuario(usuarioProfile);

        // Actualizamos el UsuarioDTO que se almacena en el navegador para que tenga el rol nuevo
        loginService.actualizarUsuarioSesion(session);

        return "redirect:/profile?id=" + idUsuarioProfile;
    }

    @GetMapping("/seguir")
    public String seguir(@RequestParam("id") Integer idUsuarioSeguir, HttpSession session,
            HttpServletRequest request) {

        // Obtenemos el id del usuario logueado
        Integer miId = ((UsuarioDTO) session.getAttribute("usuario")).getIdUsuario();
        miembrosService.seguirUsuario( miId ,idUsuarioSeguir);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/miembros");
    }

    @GetMapping("/dejarSeguir")
    public String dejarSeguir(@RequestParam("id") Integer idUsuario, HttpSession session,
            HttpServletRequest request) {

        // Obtenemos el id del usuario logueado
        Integer miId = ((UsuarioDTO) session.getAttribute("usuario")).getIdUsuario();
        miembrosService.dejarDeSeguirUsuario( miId ,idUsuario);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/miembros");
    }


    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam("id") Integer idUsuario, HttpSession session){

        miembrosService.eliminarUsuario(idUsuario);
        if( session.getAttribute(USUARIO_SESION) != null && ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario().equals(idUsuario) ) {
            loginService.logOut(session);
        }

        return "redirect:/miembros";
    }

    @GetMapping("/cambioRol")
    public String cambioRol(@RequestParam("id") Integer idUsuario, HttpSession session) {

        miembrosService.editarRolPremium(idUsuario);

        // Actualizamos el UsuarioDTO que se almacena en el navegador para que tenga el rol nuevo
        loginService.actualizarUsuarioSesion(session);

        return "redirect:/profile?id=" + idUsuario;
    }

}
