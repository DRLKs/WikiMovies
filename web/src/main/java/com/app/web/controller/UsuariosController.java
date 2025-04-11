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

    private final String MENSAJE_USUARIO_EN_USO = "El usuario indicado ya está en eso";
    private final String MENSAJE_CORREO_EN_USO = "El email indicado ya está registrado";
    private final String MENSAJE_CONTRASENA_NO_COINCIDE = "Las contraseñas indicadas no coinciden";

    @Autowired
    UsuariosRepositorio usuarioRepositorio;

    @GetMapping("/")
    public String init(HttpSession session) {
        if(estaAutenticado(session)) {
            return "redirect:/welcome";
        }else {
            return "login";
        }
    }

    @GetMapping("/login")
    public String index(@RequestParam("email") String correoElectronico,
                        @RequestParam("pwd") String contrasena, Model model, HttpSession session) {
        if(!estaAutenticado(session)) {
            if( this.usuarioRepositorio.existsBycorreoElectronico(correoElectronico) ){
                model.addAttribute("mensaje", MENSAJE_CORREO_EN_USO);
                return "login";
            }

            Usuario usuarioAutenticado = this.usuarioRepositorio.autenticaUsuario(correoElectronico, Hash.obtenerSHA256(contrasena));
            if ( usuarioAutenticado == null ){
                model.addAttribute("mensaje", MENSAJE_USUARIO_EN_USO);
                return "login";
            }
            session.setAttribute("user", usuarioAutenticado);
        }
        return "welcome";

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
            model.addAttribute("mensaje", MENSAJE_USUARIO_EN_USO);
            return "signup";
        }

        if( this.usuarioRepositorio.existsBycorreoElectronico(correoElectronico) ){
            model.addAttribute("mensaje", MENSAJE_CORREO_EN_USO);
            return "signup";
        }

        if ( !contrasena.equals( contrasenaConfirm ) ){
            model.addAttribute("mensaje", MENSAJE_CONTRASENA_NO_COINCIDE);
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setContrasenaHash( Hash.obtenerSHA256(contrasena) );

        this.usuarioRepositorio.save(usuario);

        return "redirect:/";
    }
}
