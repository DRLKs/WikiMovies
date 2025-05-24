package com.app.web.entity;

import com.app.web.dto.ListaDTO;
import com.app.web.dto.PeliculaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "peliculas")
public class Pelicula implements com.app.web.dto.DTO<PeliculaDTO> {

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

    @Column(name = "poster", length = 200)
    private String poster;

    @OneToMany(mappedBy = "idPelicula")
    private Set<Crew> crews = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "peliculasFavoritas")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "peliculas")
    private Set<Lista> listas = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "peliculas")
    private Set<Etiqueta> etiquetas = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "peliculas")
    private Set<Genero> generos = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "peliculas")
    private Set<Idioma> idiomas = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "peliculas")
    private Set<Paisproduccion> paisproduccions = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "pelicula_productora", joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_productora"))
    private Set<Productora> productoras = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Pelicula pelicula = (Pelicula) o;
        return Objects.equals(id, pelicula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public PeliculaDTO toDTO() {
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setId(id);
        peliculaDTO.setTitulo(titulo);
        peliculaDTO.setFechaEstreno(fechaEstreno);
        peliculaDTO.setPresupuesto(presupuesto);
        peliculaDTO.setIngresos(ingresos);
        peliculaDTO.setDuracion(duracion);
        peliculaDTO.setDescripcion(descripcion);
        peliculaDTO.setEnlace(enlace);
        peliculaDTO.setIdiomaOriginal(idiomaOriginal);
        peliculaDTO.setPopularidad(popularidad);
        peliculaDTO.setEstatus(estatus);
        peliculaDTO.setEslogan(eslogan);
        peliculaDTO.setTitulooriginal(titulooriginal);
        peliculaDTO.setMediaVotos(mediaVotos);
        peliculaDTO.setNumeroVotos(numeroVotos);
        peliculaDTO.setPoster(poster);

        // Convertir relaciones a IDs
        if (crews != null) {
            crews.forEach(crew -> peliculaDTO.getCrewsId().add(crew.getId()));
        }

        if (usuarios != null) {
            usuarios.forEach(usuario -> peliculaDTO.getUsuariosId().add(usuario.getId()));
        }

        if (listas != null) {
            listas.forEach(lista -> peliculaDTO.getListasId().add(lista.getId()));
        }

        if (etiquetas != null) {
            etiquetas.forEach(etiqueta -> peliculaDTO.getEtiquetasId().add(etiqueta.getId()));
        }

        // Pasar relaciones ligeras directamente
        peliculaDTO.setGeneros(generos);
        peliculaDTO.setIdiomas(idiomas);
        peliculaDTO.setPaisproduccions(paisproduccions);

        return peliculaDTO;
    }
}