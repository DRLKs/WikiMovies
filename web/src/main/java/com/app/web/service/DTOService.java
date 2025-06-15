package com.app.web.service;

import com.app.web.dto.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DTOService<DTOClass, EntityClass> {

    protected List<DTOClass> entity2DTO(Collection<EntityClass> entidades) {
        List<DTOClass> lista = new ArrayList<>();
        for (EntityClass entidad : entidades) {
            if (entidad instanceof DTO) {
                DTO<DTOClass> clase = (DTO<DTOClass>) entidad;
                lista.add(clase.toDTO());
            }
        }
        return lista;
    }

}