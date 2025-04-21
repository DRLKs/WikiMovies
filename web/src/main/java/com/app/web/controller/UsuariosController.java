package com.app.web.controller;

import com.app.web.entity.Usuario;
import com.app.web.utils.Hash;
import com.app.web.dao.UsuariosRepositorio;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UsuariosController extends BaseControlador {

    @Autowired
    UsuariosRepositorio usuarioRepositorio;

    /**
     * Función que abre el apartado de LOGIN
     * @param session Sesión del usuario, en caso de que esté loggeado se cierra
     * @return Redirección
     */
    @GetMapping("/login")
    public String abrirLogin( HttpSession session) {

        if(estaAutenticado(session)) {
            return "redirect:/welcome";
        }

        return "login";

    }

    /**
     *
     * @param correoElectronico Email seleccionada por el usuario
     * @param contrasena Contraseña escrita por el usuario
     * @param model Modelo
     * @param session Sesión
     * @return Redirección
     */
    @PostMapping("/log")
    public String iniciarSesion(@RequestParam("email") String correoElectronico,
                                @RequestParam("pwd") String contrasena,
                                @RequestParam(value = "remember", defaultValue = "false") Boolean remember,
                                Model model, HttpSession session,
                                HttpServletRequest request, HttpServletResponse response) {

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

        session.setAttribute("usuario", usuarioAutenticado);

        if (remember) {
            // Usar un nombre personalizado para evitar conflictos con JSESSIONID
            Cookie userCookie = new Cookie("wikimovies_user_session", session.getId());
            userCookie.setHttpOnly(true);
            userCookie.setMaxAge(60 * 60 * 24 * 30); // 30 días
            userCookie.setPath("/");
            response.addCookie(userCookie);
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String desLoguear(HttpSession session, HttpServletRequest request, 
                            HttpServletResponse response) {
        session.removeAttribute("usuario");
        
        // Eliminar la cookie de sesión
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("wikimovies_user_session".equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String abrirSignup(){

        return "signup";
    }

    @PostMapping("/crearCuenta")
    public String crearCuenta(@RequestParam("username") String nombreUsuario,
                              @RequestParam("email") String correoElectronico,
                              @RequestParam("pwd") String contrasena,
                              @RequestParam("pwd_confirm") String contrasenaConfirm,Model model) {

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

        if ( !contrasena.equals( contrasenaConfirm ) ){
            String msg = "Las contraseñas indicadas no coinciden";
            model.addAttribute("mensaje", msg);
            return "signup";
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setContrasenaHash( Hash.obtenerSHA256(contrasena) );

        this.usuarioRepositorio.save(usuario);

        return "redirect:/login";
    }
}
