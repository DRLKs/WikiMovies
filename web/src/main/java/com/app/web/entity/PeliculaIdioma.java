package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pelicula_idioma")
public class PeliculaIdioma {
    @EmbeddedId
    private PeliculaIdiomaId id;

    @MapsId("idPelicula")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private com.app.web.entity.Pelicula idPelicula;

    @MapsId("idIdioma")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_idioma", nullable = false)
    private Idioma idIdioma;

}