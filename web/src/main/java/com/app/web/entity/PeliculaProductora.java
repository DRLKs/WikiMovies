package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pelicula_productora")
public class PeliculaProductora {
    @EmbeddedId
    private PeliculaProductoraId id;

    @MapsId("idPelicula")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private com.app.web.entity.Pelicula idPelicula;

    @MapsId("idProductora")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_productora", nullable = false)
    private com.app.web.entity.Productora idProductora;

}