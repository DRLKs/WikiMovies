package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "peliculas")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula", nullable = false)
    private Integer id;

    @Column(name = "titulo", length = 225)
    private String titulo;

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    @Column(name = "presupuesto")
    private Long presupuesto;

    @Column(name = "ingresos")
    private Long ingresos;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "enlace", length = 200)
    private String enlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idioma_original")
    private Idioma idiomaOriginal;

    @Column(name = "popularidad")
    private Float popularidad;

    @Column(name = "estatus", length = 50)
    private String estatus;

    @Column(name = "eslogan", length = 100)
    private String eslogan;

    @Column(name = "titulooriginal")
    private String titulooriginal;

    @Column(name = "media_votos")
    private Float mediaVotos;

    @Column(name = "numero_votos")
    private Integer numeroVotos;

    @OneToMany(mappedBy = "idPelicula")
    private Set<Crew> crews = new LinkedHashSet<>();

    @ManyToMany
    private Set<com.app.web.entity.Usuario> usuarios = new LinkedHashSet<>();

    @ManyToMany
    private Set<Lista> listas = new LinkedHashSet<>();

    @ManyToMany
    private Set<Etiqueta> etiquetas = new LinkedHashSet<>();

    @ManyToMany
    private Set<Genero> generos = new LinkedHashSet<>();

    @ManyToMany
    private Set<Idioma> idiomas = new LinkedHashSet<>();

    @ManyToMany
    private Set<Paisproduccion> paisproduccions = new LinkedHashSet<>();

    @ManyToMany
    private Set<com.app.web.entity.Productora> productoras = new LinkedHashSet<>();

}