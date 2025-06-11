package com.app.web.service;

import com.app.web.dao.IdiomasRepository;
import com.app.web.dto.IdiomaDTO;
import com.app.web.entity.Idioma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdiomasService extends DTOService<IdiomaDTO, Idioma> {

    @Autowired IdiomasRepository idiomasRepository;

    public List<IdiomaDTO> getAllIdiomas(){
        return this.entity2DTO(idiomasRepository.findAll());
    }

    public IdiomaDTO getIdiomaById(int id){
        return idiomasRepository.getReferenceById(id).toDTO();
    }
}
