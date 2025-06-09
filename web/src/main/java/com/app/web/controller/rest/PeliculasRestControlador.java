package com.app.web.controller.rest;

import com.app.web.dto.PeliculaDTO;
import com.app.web.service.PeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/peliculas")
public class PeliculasRestControlador {

    @Autowired PeliculasService peliculasService;

    @GetMapping("/")
    public List<PeliculaDTO> getAllPeliculas(){
        return peliculasService.getAllPeliculasDTO();
    }

    @GetMapping("/{id}")
    public PeliculaDTO getPeliculaById(@PathVariable Integer id){
        return peliculasService.getPeliculaDTOById(id);
    }

    @GetMapping("/titulo/{titulo}")
    public List<PeliculaDTO> getPeliculasFiltro(@PathVariable("titulo") String titulo){
        return peliculasService.getAllPeliculasDTOByTitulo(titulo);
    }

    @GetMapping("/genero/{idGenero}")
    public List<PeliculaDTO> getPeliculasGenero(@PathVariable("idGenero") Integer idGenero){
        return peliculasService.getAllPeliculasDTOByTituloYGenero("", new Integer[]{idGenero});
    }

    @PostMapping("/")
    public void guardarPelicula(@RequestBody PeliculaDTO peliculaDTO){
        peliculasService.guardarPeliculaDTO(peliculaDTO);
    }
}
