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
public class PeliculaIdiomaId implements java.io.Serializable {
    private static final long serialVersionUID = -1216345090646670604L;
    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "id_idioma", nullable = false)
    private Integer idIdioma;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeliculaIdiomaId entity = (PeliculaIdiomaId) o;
        return Objects.equals(this.idPelicula, entity.idPelicula) &&
                Objects.equals(this.idIdioma, entity.idIdioma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPelicula, idIdioma);
    }

}