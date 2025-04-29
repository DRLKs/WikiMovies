package com.app.web.controller;

import com.app.web.entity.Usuario;
import com.app.web.ui.UsuarioLogin;
import com.app.web.ui.UsuarioSignup;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

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
    public String abrirLogin(Model model, HttpServletRequest request, HttpSession session) {

        if(estaAutenticado(request, session)) {
            return "redirect:/welcome";
        }

        model.addAttribute("usuarioLogin", new UsuarioLogin());
        return "login";
    }

    /**
     *
     * @param usuarioLogin DTO de usuario específica para el login de una cuenta
     * @param model Modelo
     * @param session Sesión
     * @return Redirección
     */
    @PostMapping("/log")
    public String iniciarSesion(@ModelAttribute()UsuarioLogin usuarioLogin,
                                Model model, HttpSession session,
                                HttpServletRequest request, HttpServletResponse response) {

        String correoElectronico = usuarioLogin.getEmail();
        String contrasena = usuarioLogin.getPassword();
        boolean remember = usuarioLogin.getRemember();

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

    @GetMapping("/logout")
    public String desLoguear(HttpSession session, HttpServletRequest request, 
                            HttpServletResponse response) {
        session.removeAttribute("usuario");
        
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
        
        return "redirect:/";
    }

    /**
     * Función que abre el apartado de SIGNUP
     * @return Redirección
     */
    @GetMapping("/signup")
    public String abrirSignup(Model model){

        model.addAttribute("usuarioSignup", new UsuarioSignup());
        return "signup";
    }

    /**
     * Función que crea la cuenta de un nuevo usuario
     * @param usuarioSignup DTO de usuario específico para la creación de una cuenta
     * @param model
     * @return
     */
    @PostMapping("/crearCuenta")
    public String crearCuenta(@ModelAttribute() UsuarioSignup usuarioSignup,Model model) {

        String nombreUsuario = usuarioSignup.getUsername();
        String correoElectronico = usuarioSignup.getEmail();
        String contrasena = usuarioSignup.getPassword();
        String contrasenaConfirm = usuarioSignup.getPasswordConfirmed();

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
        usuario.setCreacionCuentaFecha(LocalDate.now());

        this.usuarioRepositorio.save(usuario);

        return "redirect:/login";
    }
}
