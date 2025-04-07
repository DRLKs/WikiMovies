package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pelicula_genero")
public class PeliculaGenero {
    @EmbeddedId
    private PeliculaGeneroId id;

    @MapsId("idPelicula")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private com.app.web.entity.Pelicula idPelicula;

    @MapsId("idGenero")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero idGenero;

}