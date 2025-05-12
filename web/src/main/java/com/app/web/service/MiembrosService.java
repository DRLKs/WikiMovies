package com.app.web.service;

import com.app.web.dao.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiembrosService {

    @Autowired UsuariosRepositorio usuariosRepositorio;


}
