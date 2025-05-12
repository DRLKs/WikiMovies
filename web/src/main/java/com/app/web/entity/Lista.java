package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lista")
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "lista_peliculas",
            joinColumns = @JoinColumn(name = "id_lista"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<Pelicula> peliculas = new LinkedHashSet<>();

    @Column(name = "descripcion", nullable = false, length = 400)
    private String descripcion;

    @Column(name = "imgURL", length = 250)
    private String imgURL;

}