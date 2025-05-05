package com.app.web.ui;

import lombok.Data;

import java.util.List;

@Data
public class NuevaLista {
    String nombre;
    String descripcion;
    List<Integer> peliculasId;
}
