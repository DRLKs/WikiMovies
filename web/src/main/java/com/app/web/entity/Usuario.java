package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre_usuario", length = 100)
    private String nombreUsuario;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "contrasena_hash")
    private String contrasenaHash;

    @Column(name = "rol")
    private Integer rol;

    @ManyToMany
    private Set<Pelicula> peliculas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario")
    private Set<Lista> listas = new LinkedHashSet<>();

}