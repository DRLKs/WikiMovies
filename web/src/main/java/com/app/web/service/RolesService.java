package com.app.web.service;

import com.app.web.dao.RolesRepository;
import com.app.web.dto.RolesDTO;
import com.app.web.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class RolesService extends DTOService<RolesDTO, Role> {

    @Autowired RolesRepository rolesRepository;

    public void cargarModelosRoles(Model model){
        model.addAttribute("rolesUsuarios", this.entity2DTO( rolesRepository.findAll() ) );
    }
}
