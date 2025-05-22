package com.app.web.service;

import com.app.web.dao.ListaRepository;
import com.app.web.entity.Lista;
import com.app.web.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static com.app.web.utils.Constantes.LISTA_VISTAS;

@Service
public class ListasService {

    @Autowired ListaRepository listaRepository;


    public List<Lista> getListas() {
        return listaRepository.findAll();
    }

    public Lista getListabyId(int id) {
        Lista lista = null;
        if(id > 0){
            lista = listaRepository.getReferenceById(id);
        }
        return lista;
    }

    public Lista getListaFavoritas(int id) {
        Lista lista = null;
        if(id > 0){
            lista = listaRepository.getListaFavoritas(id);
        }
        return lista;
    }

    public Lista getListaVistas(int id) {
        Lista lista = null;
        if(id > 0){
            lista = listaRepository.getListaVistas(id);
        }
        return lista;
    }

    public List<Lista> getListaUsuario(int id) {
        List<Lista> lista = null;
        if(id > 0){
            lista = listaRepository.getListasUsuario(id);
        }
        return lista;
    }

    public void guardarPelicula(Lista lista) {
        if(lista.getId() > 0){
            listaRepository.save(lista);
        }

    }




}
