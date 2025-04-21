package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "idiomas")
public class Idioma {
    @Id
    @Column(name = "id_idioma", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "pelicula_idioma",
            joinColumns = @JoinColumn(name = "id_idioma"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<com.app.web.entity.Pelicula> peliculas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idiomaOriginal")
    private Set<com.app.web.entity.Pelicula> peliculaIdiomaOriginal = new LinkedHashSet<>();

}