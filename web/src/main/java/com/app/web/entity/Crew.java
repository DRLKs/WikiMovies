package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "crew")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_crew", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private com.app.web.entity.Pelicula idPelicula;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "genero")
    private Integer genero;

    @Column(name = "id_credencial", nullable = false, length = 50)
    private String idCredencial;

    @ManyToMany
    @JoinTable(name = "crew_personaje",
            inverseJoinColumns = @JoinColumn(name = "Personaje_id_miembro"))
    private Set<com.app.web.entity.Personaje> personajes = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "crew_trabajo",
            inverseJoinColumns = @JoinColumn(name = "Trabajo_id_trabajo"))
    private Set<com.app.web.entity.Trabajo> trabajos = new LinkedHashSet<>();

}