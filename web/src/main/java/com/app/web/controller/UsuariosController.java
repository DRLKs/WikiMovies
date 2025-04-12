package com.app.web.controller;

import com.app.web.utils.Hash;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Usuario;
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
                                Model model, HttpSession session) {

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
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String desLoguear(HttpSession session) {
        session.removeAttribute("usuario");

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
