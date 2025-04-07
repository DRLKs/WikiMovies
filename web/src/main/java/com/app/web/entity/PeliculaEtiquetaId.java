package com.app.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PeliculaEtiquetaId implements java.io.Serializable {
    private static final long serialVersionUID = 4002888058665963549L;
    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "id_etiqueta", nullable = false)
    private Integer idEtiqueta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeliculaEtiquetaId entity = (PeliculaEtiquetaId) o;
        return Objects.equals(this.idEtiqueta, entity.idEtiqueta) &&
                Objects.equals(this.idPelicula, entity.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtiqueta, idPelicula);
    }

}