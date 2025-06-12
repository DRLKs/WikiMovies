package com.app.web.entity;

import com.app.web.dto.DTO;
import com.app.web.dto.GeneroUsuarioDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "generousuario")
public class Generousuario implements DTO<GeneroUsuarioDTO> {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @OneToMany(mappedBy = "genero")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    @Override
    public GeneroUsuarioDTO toDTO() {
        GeneroUsuarioDTO dto = new GeneroUsuarioDTO();
        dto.setId(id);
        dto.setNombre(nombre);
        return dto;
    }
}