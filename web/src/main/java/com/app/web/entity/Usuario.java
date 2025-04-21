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

    @Column(name = "avatarUrl", length = 200)
    private String avatarUrl;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Lista> listas = new LinkedHashSet<>();


    @ManyToMany
    @JoinTable(name = "favoritos",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<Pelicula> peliculasFavoritas = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "seguidores",
            joinColumns = @JoinColumn(name = "idUsuario1"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario2"))
    private Set<Usuario> seguidores = new LinkedHashSet<>();

    /*
    @ManyToMany
    @JoinTable(
            name = "seguidores",
            joinColumns = @JoinColumn(name = "idUsuario1"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario1")
    )
    private Set<Usuario> seguidores = new LinkedHashSet<>();

    // Usuarios a los que este usuario sigue
    @ManyToMany
    @JoinTable(
            name = "seguidores",
            joinColumns = @JoinColumn(name = "idUsuario2"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario2")
    )
    private Set<Usuario> seguidos = new LinkedHashSet<>();

    * */

}