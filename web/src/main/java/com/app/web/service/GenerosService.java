package com.app.web.service;

import com.app.web.dao.GenerosRepository;
import com.app.web.dto.GeneroDTO;
import com.app.web.entity.Genero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerosService extends DTOService<GeneroDTO, Genero>{

    @Autowired GenerosRepository generosRepository;

    public GeneroDTO getGenero(Integer id){
        Genero genero = generosRepository.getReferenceById(id);
        return genero.toDTO();
    }

    public List<GeneroDTO> getAllGeneros(){
        List<Genero> generos = generosRepository.findAll();
        return this.entity2DTO(generos);
    }
}
