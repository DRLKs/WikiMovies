package com.app.web.entity;

import com.app.web.dto.DTO;
import com.app.web.dto.ListaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lista")
public class Lista implements DTO<ListaDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "lista_peliculas", joinColumns = @JoinColumn(name = "id_lista"), inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<Pelicula> peliculas = new LinkedHashSet<>();

    @Column(name = "descripcion", nullable = false, length = 400)
    private String descripcion;

    @Column(name = "imgURL", length = 600)
    private String imgURL;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;


    public ListaDTO toDTO() {
        ListaDTO listaDTO = new ListaDTO();
        listaDTO.setId(this.id);
        listaDTO.setDescripcion(this.descripcion);
        listaDTO.setNombre(this.nombre);
        listaDTO.setFotoUrl(this.imgURL);
        // Añadir información del usuario creador
        if (this.idUsuario != null) {
            listaDTO.setUsuarioId(this.idUsuario.getId());
            listaDTO.setNombreUsuario(this.idUsuario.getNombreUsuario());
        }
        // Convertir los IDs de películas
        this.peliculas.forEach(pelicula -> listaDTO.getPeliculasId().add(pelicula.getId()));
        return listaDTO;
    }

}