package com.app.web.dto;

import lombok.Data;

import java.util.Set;

@Data
public class IdiomaDTO {
    Integer id;
    String nombre;
    Set<PeliculaDTO> peliculas;
}
