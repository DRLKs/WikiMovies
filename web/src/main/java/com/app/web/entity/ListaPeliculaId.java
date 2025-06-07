package com.app.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ListaPeliculaId implements java.io.Serializable {
    private static final long serialVersionUID = 1895170678652302635L;
    @Column(name = "id_lista", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer idLista;

    @Column(name = "id_pelicula", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer idPelicula;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListaPeliculaId entity = (ListaPeliculaId) o;
        return Objects.equals(this.idLista, entity.idLista) &&
                Objects.equals(this.idPelicula, entity.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLista, idPelicula);
    }

}