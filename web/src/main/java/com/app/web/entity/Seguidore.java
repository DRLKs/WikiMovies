package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "seguidores")
public class Seguidore {
    @EmbeddedId
    private SeguidoreId id;

    @MapsId("idUsuario1")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idUsuario1", nullable = false)
    private com.app.web.entity.Usuario idUsuario1;

    @MapsId("idUsuario2")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idUsuario2", nullable = false)
    private com.app.web.entity.Usuario idUsuario2;

}