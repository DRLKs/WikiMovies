package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "pelicula_etiqueta")
public class PeliculaEtiqueta {
    @EmbeddedId
    private PeliculaEtiquetaId id;

    @MapsId("idPelicula")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private com.app.web.entity.Pelicula idPelicula;

    @MapsId("idEtiqueta")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_etiqueta", nullable = false)
    private Etiqueta idEtiqueta;

}