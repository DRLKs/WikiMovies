package com.app.web.service;

import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UsuarioService {

    @Autowired private UsuariosRepositorio usuarioRepositorio;

    protected boolean estaAutenticado(HttpSession session) {return session.getAttribute("usuario") != null ;}



    public Usuario buscarUsuario(Integer idUsuario) {
        Usuario usuario = null;
        if(idUsuario > 0){
            usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        }
        return usuario;
    }

    public List<Usuario> usuarioSeguidos(int id){
        List<Usuario> seguidos = new ArrayList<>();
        if(id > 0){
            seguidos = usuarioRepositorio.getSeguidos(id);
        }
        return seguidos;
    }


}
