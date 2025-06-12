package com.app.web.service;

import com.app.web.dao.GenerosUsuariosRepository;
import com.app.web.dto.GeneroUsuarioDTO;
import com.app.web.entity.Generousuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class GenerosUsuariosService extends DTOService<GeneroUsuarioDTO, Generousuario> {

    @Autowired GenerosUsuariosRepository generosUsuariosRepository;

    public void cargarModelosGenerosUsuarios(Model model){

        model.addAttribute("generosUsuarios", this.entity2DTO(generosUsuariosRepository.findAll()));
    }

}
