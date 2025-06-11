package com.app.web.entity;

import com.app.web.dto.DTO;
import com.app.web.dto.IdiomaDTO;
import com.app.web.dto.PeliculaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "idiomas")
public class Idioma implements DTO<IdiomaDTO> {
    @Id
    @Column(name = "id_idioma", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "pelicula_idioma",
            joinColumns = @JoinColumn(name = "id_idioma"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<com.app.web.entity.Pelicula> peliculas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idiomaOriginal")
    private Set<com.app.web.entity.Pelicula> peliculaIdiomaOriginal = new LinkedHashSet<>();

    @Override
    public IdiomaDTO toDTO() {
        IdiomaDTO idiomaDTO = new IdiomaDTO();
        idiomaDTO.setId(id);
        idiomaDTO.setNombre(nombre);
        Set<PeliculaDTO> peliculasDTO = new HashSet<>();
        for( Pelicula pelicula : peliculas ){
            peliculasDTO.add(pelicula.toDTO());
        }
        idiomaDTO.setPeliculas( peliculasDTO );
        return idiomaDTO;
    }
}