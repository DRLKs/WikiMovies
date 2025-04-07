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
public class PeliculaGeneroId implements java.io.Serializable {
    private static final long serialVersionUID = -8038598115081864737L;
    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "id_genero", nullable = false)
    private Integer idGenero;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeliculaGeneroId entity = (PeliculaGeneroId) o;
        return Objects.equals(this.idGenero, entity.idGenero) &&
                Objects.equals(this.idPelicula, entity.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGenero, idPelicula);
    }

}