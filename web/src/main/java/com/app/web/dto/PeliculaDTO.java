package com.app.web.dto;

import com.app.web.entity.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class PeliculaDTO {
      private Integer id;
      private String titulo;
      private LocalDate fechaEstreno;
      private Long presupuesto;
      private Long ingresos;
      private Integer duracion;
      private String descripcion;
      private String enlace;
      private Idioma idiomaOriginal;
      private Float popularidad;
      private String estatus;
      private String eslogan;
      private String titulooriginal;
      private Float mediaVotos;
      private Integer numeroVotos;
      private String poster;

      // Para mantener las relaciones, guardamos los IDs y nombres cuando sea
      // necesario
      private Set<Integer> crewsId = new LinkedHashSet<>();
      private Set<Integer> usuariosId = new LinkedHashSet<>();
      private Set<Integer> listasId = new LinkedHashSet<>();
      private Set<Integer> etiquetasId = new LinkedHashSet<>();

      // Para géneros guardamos el objeto completo ya que es ligero y se usa mucho
      private Set<Genero> generos = new LinkedHashSet<>();
      private Set<Idioma> idiomas = new LinkedHashSet<>();
      private Set<Paisproduccion> paisproduccions = new LinkedHashSet<>();

}
