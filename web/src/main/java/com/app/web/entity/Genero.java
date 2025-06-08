package com.app.web.entity;

import com.app.web.dto.DTO;
import com.app.web.dto.GeneroDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "generos")
public class Genero implements DTO<GeneroDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToMany(mappedBy = "generos")
    private Set<Pelicula> peliculas = new LinkedHashSet<>();


    @Override
    public GeneroDTO toDTO() {
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setId(id);
        generoDTO.setNombre(nombre);
        return generoDTO;
    }
}