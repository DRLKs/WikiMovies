package com.app.web.controller;

import com.app.web.service.LoginService;
import com.app.web.service.UsuarioService;
import com.app.web.ui.UsuarioLogin;
import com.app.web.ui.UsuarioSignup;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UsuariosController extends BaseControlador {

    @Autowired LoginService loginService;
    @Autowired UsuarioService usuariosService;

    /**
     * Función que abre el apartado de LOGIN
     * @param session Sesión del usuario, en caso de que esté loggeado se cierra
     * @return Redirección
     */
    @GetMapping("/login")
    public String abrirLogin(Model model, HttpServletRequest request, HttpSession session) {

        return loginService.abrirLogin(model, request, session);
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
                                Model model, HttpSession session) {

        String correoElectronico = usuarioLogin.getEmail();
        String contrasena = usuarioLogin.getPassword();

        return loginService.log(correoElectronico,contrasena,model,session);

    }

    @GetMapping("/logout")
    public String desLoguear(HttpSession session) {
        loginService.logOut(session);
        
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
     * @return JSP
     */
    @PostMapping("/crearCuenta")
    public String crearCuenta(@ModelAttribute() UsuarioSignup usuarioSignup,Model model) {

        String nombreUsuario = usuarioSignup.getUsername();
        String correoElectronico = usuarioSignup.getEmail();
        String contrasena = usuarioSignup.getPassword();
        String contrasenaConfirm = usuarioSignup.getPasswordConfirmed();

        if ( !contrasena.equals( contrasenaConfirm ) ){
            String msg = "Las contraseñas indicadas no coinciden";
            model.addAttribute("mensaje", msg);
            return "signup";
        }
        
        return usuariosService.crearCuenta(nombreUsuario, correoElectronico, contrasena, model);
    }
}
