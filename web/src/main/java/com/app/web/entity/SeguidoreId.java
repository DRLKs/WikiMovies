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
public class SeguidoreId implements java.io.Serializable {
    private static final long serialVersionUID = -5820326742739384733L;
    @Column(name = "idUsuario1", nullable = false)
    private Integer idUsuario1;

    @Column(name = "idUsuario2", nullable = false)
    private Integer idUsuario2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeguidoreId entity = (SeguidoreId) o;
        return Objects.equals(this.idUsuario1, entity.idUsuario1) &&
                Objects.equals(this.idUsuario2, entity.idUsuario2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario1, idUsuario2);
    }

}