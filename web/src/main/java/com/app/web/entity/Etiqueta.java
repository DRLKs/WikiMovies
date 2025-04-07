package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "etiquetas")
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etiqueta", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "pelicula_etiqueta",
            joinColumns = @JoinColumn(name = "id_etiqueta"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<com.app.web.entity.Pelicula> peliculas = new LinkedHashSet<>();

}