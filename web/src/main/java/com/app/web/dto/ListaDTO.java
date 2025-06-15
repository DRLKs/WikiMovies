package com.app.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListaDTO {
    private String fotoUrl;
    private String nombre;
    private String descripcion;
    private List<PeliculaDTO> peliculas;
    // Referencia al ID de la lista, útil para ediciones
    private Integer id;
    // Información del usuario creador
    private Integer usuarioId;
    private String nombreUsuario;
}
