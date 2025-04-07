package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "generos")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "pelicula_genero",
            joinColumns = @JoinColumn(name = "id_genero"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<com.app.web.entity.Pelicula> peliculas = new LinkedHashSet<>();

}