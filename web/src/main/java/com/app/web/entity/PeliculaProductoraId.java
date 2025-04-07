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
public class PeliculaProductoraId implements java.io.Serializable {
    private static final long serialVersionUID = -8046005786562961439L;
    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "id_productora", nullable = false)
    private Integer idProductora;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeliculaProductoraId entity = (PeliculaProductoraId) o;
        return Objects.equals(this.idPelicula, entity.idPelicula) &&
                Objects.equals(this.idProductora, entity.idProductora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPelicula, idProductora);
    }

}