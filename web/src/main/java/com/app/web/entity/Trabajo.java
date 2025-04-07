package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "trabajo")
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajo", nullable = false)
    private Integer id;

    @Column(name = "departamento", length = 50)
    private String departamento;

    @Column(name = "trabajo", length = 50)
    private String trabajo;

    @ManyToMany(mappedBy = "trabajos")
    private Set<Crew> crews = new LinkedHashSet<>();

}