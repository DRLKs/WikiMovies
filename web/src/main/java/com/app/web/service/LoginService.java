package com.app.web.service;

import com.app.web.dao.UsuariosRepositorio;
import com.app.web.dto.UsuarioDTO;
import com.app.web.entity.Usuario;
import com.app.web.ui.UsuarioLogin;
import com.app.web.utils.Hash;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import static com.app.web.utils.Constantes.USUARIO_SESION;

@Service
public class LoginService extends UsuarioService {

    @Autowired UsuariosRepositorio usuarioRepositorio;


    public String abrirLogin(Model model, HttpServletRequest request, HttpSession session) {
        if(estaAutenticado(request, session)) {
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
        logOut(session);
        guardarSesion( usuarioRepositorio.getReferenceById(idUsuarioSesion).toDTO(), session);
    }

    /**
     * Elimina la sesión del usuario del sistem de memoria, lo desloguea
     */
    public void logOut(HttpSession session) {
        session.removeAttribute(USUARIO_SESION);
    }
}
