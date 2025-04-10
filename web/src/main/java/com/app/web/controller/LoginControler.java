package com.app.web.controller;

import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginControler {

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @GetMapping("/")
    public String index(Model model) {
        List<Usuario> usuarios = usuariosRepositorio.findAll();
        model.addAttribute("usuarios", usuarios);
        return "login";
    }

    @GetMapping("/signup")
    public String abrirSignup(Model model) {
        return "signup";
    }
}
