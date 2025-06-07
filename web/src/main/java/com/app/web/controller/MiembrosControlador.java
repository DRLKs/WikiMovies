package com.app.web.controller;

import com.app.web.dao.GenerosRepository;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.dto.ListaDTO;
import com.app.web.entity.Genero;
import com.app.web.entity.Lista;
import com.app.web.entity.Usuario;
import com.app.web.ui.FiltroBusquedaDTO;
import com.app.web.ui.UsuarioProfile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.app.web.utils.Constantes.USUARIO_SESION;
import static com.app.web.utils.Constantes.USER_ADMIN;

@Controller
public class MiembrosControlador extends BaseControlador {

    @Autowired UsuariosRepositorio usuarioRepositorio;
    @Autowired GenerosRepository generosRepositorio;

    /**
     * Controlador de la petición del sistema al cargar todos los miembros de
     * WikiMovies
     */
    @GetMapping("/miembros")
    public String mostrarMiembros(Model model) {

        // List<Usuario> usuarios = usuarioRepositorio.usuariosOrdenadosPorSeguidores();
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        model.addAttribute("miembros", usuarios);

        return "miembros";
    }

    @GetMapping("/miembros/filtro")
    public String mostrarFiltro(@RequestParam("nombre") String nombre,  Model model) {

        // List<Usuario> usuarios = usuarioRepositorio.usuariosOrdenadosPorSeguidores();
        List<Usuario> usuarios = usuarioRepositorio.findByNombre(nombre);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        model.addAttribute("miembros", usuarios);

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

        Usuario usuario = usuarioRepositorio.getReferenceById(id);
        model.addAttribute(USUARIO_SESION, usuario);

        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());

        // Introducimos los datos necesarios en el DTO
        UsuarioProfile usuarioProfile = new UsuarioProfile();
        usuarioProfile.setId(usuario.getId());
        usuarioProfile.setAvatar(usuario.getAvatarUrl());
        usuarioProfile.setBiografia(usuario.getBiografia());
        usuarioProfile.setNombreUsuario(usuario.getNombreUsuario());
        usuarioProfile.setGenero(usuario.getGenero());
        usuarioProfile.setFechaNacimiento(usuario.getNacimientoFecha());
        usuarioProfile.setRol(usuario.getRol());
        model.addAttribute("usuarioProfile", usuarioProfile);

        // Convertir las listas del usuario a DTOs
        List<ListaDTO> listasDTO = new ArrayList<>();
        if (usuario.getListas() != null) {
            for (Lista lista : usuario.getListas()) {
                listasDTO.add(lista.toDTO());
            }
        }
        model.addAttribute("listasDTO", listasDTO);

        List<Usuario> seguidos = usuarioRepositorio.getSeguidos(id);
        model.addAttribute("seguidos", seguidos.size());

        return "profile";
    }

    /**
     * Controlador del formulario de actualización del Profile del Usuario
     * 
     * @param usuarioProfile Usuario del profile
     * @return JSP
     */
    @PostMapping("/profile/update")
    public String doUpdateProfile(@ModelAttribute() UsuarioProfile usuarioProfile, Model model,
            HttpServletRequest request, HttpSession session) {

        if (!estaAutenticado(request, session)) {
            return "redirect:/login";
        }

        // Comprobamos que el usuario tenga permisos para editar un perfil
        Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESION);
        Integer idUsuarioProfile = usuarioProfile.getId();


        if( !usuario.getId().equals(idUsuarioProfile) ) {
            if( usuario.getRol() != USER_ADMIN ) {
                // Alguien sin permisos intenta ajustar una cuenta
                return "redirect:/profile?id=" + idUsuarioProfile;
            }
            // El administrador ajusta una cuenta que no es la suya
            usuario = usuarioRepositorio.getReferenceById(idUsuarioProfile);
        }



        String avatar = usuarioProfile.getAvatar();
        String nombreUsuario = usuarioProfile.getNombreUsuario();
        String biografia = usuarioProfile.getBiografia();
        LocalDate fechaNacimiento = usuarioProfile.getFechaNacimiento();
        Integer genero = usuarioProfile.getGenero();
        Integer rol = usuarioProfile.getRol();


        try {

            if (avatar != null && !avatar.isEmpty()) {
                usuario.setAvatarUrl(avatar);
            }

            if (nombreUsuario != null && !nombreUsuario.isEmpty()
                    && !nombreUsuario.equals(usuario.getNombreUsuario())) {
                usuario.setNombreUsuario(nombreUsuario);
            }

            if (biografia != null && !biografia.isEmpty() && !biografia.equals(usuario.getBiografia())) {
                usuario.setBiografia(biografia);
            }

            if (fechaNacimiento != null) {

                usuario.setNacimientoFecha(fechaNacimiento);
            }

            if (genero != null) {
                usuario.setGenero(genero);
            }

            if( rol != null ){
                usuario.setRol(rol);
            }

        } catch (Exception e) {
            return "redirect:/profile?id=" + idUsuarioProfile;
        }

        this.usuarioRepositorio.save(usuario);

        return "redirect:/profile?id=" + idUsuarioProfile;
    }

    @GetMapping("/seguir")
    public String seguir(@RequestParam("id") Integer idSeguido, Model model, HttpSession session,
            HttpServletRequest request) {
        Usuario usuarioSeguido = usuarioRepositorio.getReferenceById(idSeguido);
        Usuario yo = (Usuario) session.getAttribute("usuario");
        usuarioSeguido.getSeguidores().add(yo);
        usuarioRepositorio.save(usuarioSeguido);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/miembros");
    }

    @GetMapping("/dejarSeguir")
    public String dejarSeguir(@RequestParam("id") Integer idSeguido, Model model, HttpSession session,
            HttpServletRequest request) {
        Usuario usuarioSeguido = usuarioRepositorio.getReferenceById(idSeguido);
        Usuario yo = (Usuario) session.getAttribute("usuario");
        usuarioSeguido.getSeguidores().remove(yo);
        usuarioRepositorio.save(usuarioSeguido);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/miembros");
    }


    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam("id") Integer idUsuario){

        Usuario usuario = usuarioRepositorio.getReferenceById(idUsuario);
        usuarioRepositorio.delete(usuario);

        return "redirect:/miembros";
    }

}
