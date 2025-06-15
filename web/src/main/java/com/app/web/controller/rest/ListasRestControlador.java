package com.app.web.controller.rest;

import com.app.web.dto.ListaDTO;
import com.app.web.service.ListasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/listas")
public class ListasRestControlador {

    @Autowired protected ListasService listasService;

    @GetMapping("/")
    public List<ListaDTO> obtenerTodosLasListas(){
        return listasService.getAllListasDTO();
    }

    @GetMapping("/{id}")
    public ListaDTO obtenerListaPorId(@PathVariable Integer id){
        return listasService.getListaDTOById(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarLista(@PathVariable int id){
        listasService.deleteListaById(id);
    }

    @GetMapping("/usuario/{id}")
    public List<ListaDTO> obtenersListasPorUsuario(@PathVariable Integer id){
        return listasService.getListasUsuarioNoFavoritasNoVistas(id);
    }

    @GetMapping("/favoritas/{id}")
    public ListaDTO obtenerListaFavoritosUsuario(@PathVariable Integer id){
        return listasService.getListaFavoritosUsuario(id);
    }

    @GetMapping("/vistas/{id}")
    public ListaDTO obtenerListaVistasUsuario(@PathVariable Integer id){
        return listasService.getListaVistasUsuario(id);
    }
}
