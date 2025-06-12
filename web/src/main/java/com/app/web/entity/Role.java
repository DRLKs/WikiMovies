package com.app.web.entity;

import com.app.web.dto.DTO;
import com.app.web.dto.RolesDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements DTO<RolesDTO> {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @OneToMany(mappedBy = "rol")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    @Override
    public RolesDTO toDTO() {
        RolesDTO dto = new RolesDTO();
        dto.setId(id);
        dto.setNombre(nombre);
        return dto;
    }
}