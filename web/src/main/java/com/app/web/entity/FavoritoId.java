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
public class FavoritoId implements java.io.Serializable {
    private static final long serialVersionUID = 282085197375563756L;
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FavoritoId entity = (FavoritoId) o;
        return Objects.equals(this.idUsuario, entity.idUsuario) &&
                Objects.equals(this.idPelicula, entity.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idPelicula);
    }

}