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
public class PeliculaPaisproduccionId implements java.io.Serializable {
    private static final long serialVersionUID = 3071533987028992072L;
    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "id_pais", nullable = false)
    private Integer idPais;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeliculaPaisproduccionId entity = (PeliculaPaisproduccionId) o;
        return Objects.equals(this.idPais, entity.idPais) &&
                Objects.equals(this.idPelicula, entity.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPais, idPelicula);
    }

}