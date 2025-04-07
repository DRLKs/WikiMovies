package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "personaje")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_miembro", nullable = false)
    private Integer id;

    @Column(name = "personaje", length = 50)
    private String personaje;

    @Column(name = "orden")
    private Integer orden;

    @ManyToMany(mappedBy = "personajes")
    private Set<Crew> crews = new LinkedHashSet<>();

}