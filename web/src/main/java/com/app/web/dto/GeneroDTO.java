package com.app.web.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GeneroDTO {
    Integer id;
    String nombre;
    Set<Integer> peliculasIDs;
}
