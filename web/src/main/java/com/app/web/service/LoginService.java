package com.app.web.service;

import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Usuario;
import com.app.web.ui.UsuarioLogin;
import com.app.web.utils.Hash;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                      Boolean remember,
                      Model model,
                      HttpSession session,
                      HttpServletResponse response) {

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

        session.setAttribute(USUARIO_SESION, usuarioAutenticado);

        if (remember) {
            // Crear un token único que combine ID de usuario y una marca de tiempo
            String userToken = usuarioAutenticado.getId() + ":" + System.currentTimeMillis();

            // Guardar en la cookie este token en lugar del session ID
            Cookie userCookie = new Cookie("wikimovies_user_session", userToken);
            userCookie.setHttpOnly(true);
            userCookie.setMaxAge(60 * 60 * 24 * 30); // 30 días
            userCookie.setPath("/");
            response.addCookie(userCookie);

            // Guardar en el cache de sesiones
            guardarUsuarioEnSesionCache(userToken, usuarioAutenticado);
        }

        return "redirect:/";
    }

    public void logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.removeAttribute(USUARIO_SESION);

        // Eliminar la cookie de sesión
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("wikimovies_user_session".equals(cookie.getName())) {
                    // Eliminar del cache antes de invalidar la cookie
                    eliminarUsuarioDelSesionCache(cookie.getValue());

                    // Invalidar la cookie
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
