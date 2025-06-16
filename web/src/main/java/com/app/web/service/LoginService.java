package com.app.web.service;

import com.app.web.dao.GenerosUsuariosRepository;
import com.app.web.dao.RolesRepository;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.dto.UsuarioDTO;
import com.app.web.entity.Generousuario;
import com.app.web.entity.Lista;
import com.app.web.entity.Role;
import com.app.web.entity.Usuario;
import com.app.web.ui.UsuarioLogin;
import com.app.web.utils.Hash;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.app.web.utils.Constantes.*;

@Service
public class LoginService extends UsuarioService {

    @Autowired UsuariosRepositorio usuarioRepositorio;
    @Autowired private RolesRepository rolesRepository;
    @Autowired private GenerosUsuariosRepository generosUsuariosRepository;

    @Autowired private ListasService listasService;

    public String abrirLogin(Model model, HttpSession session) {
        if(estaAutenticado(session)) {
            return "redirect:/welcome";
        }

        model.addAttribute("usuarioLogin", new UsuarioLogin());
        return "login";
    }

    public String log(String correoElectronico,
                      String contrasena,
                      Model model,
                      HttpSession session) {

        if( !this.usuarioRepositorio.existsBycorreoElectronico(correoElectronico) ){
            String msg = "El email indicado no está registrado";
            model.addAttribute("mensaje", msg);
            return "login";
        }

        Usuario usuarioAutenticado = this.usuarioRepositorio.autenticaUsuario(correoElectronico, Hash.obtenerSHA256(contrasena));
        if ( usuarioAutenticado == null ){
            String msg = "Correo y contraseña no coinciden";
            model.addAttribute("mensaje", msg);
            return "login";
        }

        UsuarioDTO usuarioAutenticadoDTO = usuarioAutenticado.toDTO();
        guardarSesion( usuarioAutenticadoDTO, session);

        return "redirect:/";
    }

    /**
     * Guardamos en la sesión del navegador un usuario el cual se debe haber identificado anteriormente
     *
     * @param usuarioAutenticado Usuario que se guardará en la sesión
     */
    private void guardarSesion(UsuarioDTO usuarioAutenticado, HttpSession session) {
        session.setAttribute(USUARIO_SESION, usuarioAutenticado);
    }

    /**
     * Actualiza la sesión del usuario
     */
    public void actualizarUsuarioSesion(HttpSession session){
        Integer idUsuarioSesion = ((UsuarioDTO) session.getAttribute(USUARIO_SESION)).getIdUsuario();
        guardarSesion( usuarioRepositorio.getReferenceById(idUsuarioSesion).toDTO(), session);
    }

    /**
     * Elimina la sesión del usuario del sistem de memoria, lo desloguea
     */
    public void logOut(HttpSession session) {
        session.removeAttribute(USUARIO_SESION);
    }

    public String crearCuenta(String nombreUsuario, String correoElectronico, String contrasena,
                              Model model) {
        if( this.usuarioRepositorio.existsByNombreUsuario(nombreUsuario) ){
            String msg = "El usuario indicado ya está en eso";
            model.addAttribute("mensaje", msg);
            return "signup";
        }

        if( this.usuarioRepositorio.existsBycorreoElectronico(correoElectronico) ){
            String msg = "El email indicado ya está registrado";
            model.addAttribute("mensaje", msg);
            return "signup";
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setContrasenaHash( Hash.obtenerSHA256(contrasena) );
        usuario.setBiografia(""); //añadido
        usuario.setCreacionCuentaFecha(LocalDate.now());

        Role rolNoIdentidicado = rolesRepository.getReferenceById(USER_NORMAL);
        usuario.setRol(rolNoIdentidicado);

        Generousuario generoNoIdentificado = generosUsuariosRepository.getReferenceById(GENDER_NOT_SPECIFIED);
        usuario.setGenero(generoNoIdentificado);

        this.usuarioRepositorio.save(usuario);


        // Creamos el conjunto donde se almacenarán las listas del usuario
        Set<Lista> listas = new HashSet<>();

        //Creamos la lista que conteine las películas favoritas del usuario
        Lista listaFav = listasService.nuevaLista(LISTA_FAVORITAS,DESCRIPCION_LISTA_FAVORITAS,IMAGEN_LISTA_FAVORITAS, usuario);
        listas.add(listaFav);

        // Creamos la lista que contiene las películas vistas
        Lista listaVistas = listasService.nuevaLista(LISTA_VISTAS,DESCRIPCION_LISTA_VISTAS,IMAGEN_LISTA_VISTAS, usuario);
        listas.add((listaVistas));

        this.listasService.guardarLista(listaVistas);
        this.listasService.guardarLista(listaFav);

        usuario.setListas(listas);
        this.usuarioRepositorio.save(usuario);

        return "redirect:/login";
    }
}
