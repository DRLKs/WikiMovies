package com.app.web.service;

import com.app.web.dao.PeliculasRepository;
import com.app.web.dto.PeliculaDTO;
import com.app.web.entity.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeliculasService extends DTOService<PeliculaDTO, Pelicula> {

    @Autowired private PeliculasRepository peliculasRepository;

    public List<PeliculaDTO> getAllPeliculasDTO() {
        List<Pelicula> peliculas = peliculasRepository.findAll();

        return this.entity2DTO(peliculas);
    }

    public PeliculaDTO getPeliculaDTOById(int id) {
        return peliculasRepository.getPeliculaById(id).toDTO();
    }

    public List<Pelicula> buscarPeliculaXTitulo(String nombre) {
        return peliculasRepository.findByTitulo(nombre);
    }

    public List<PeliculaDTO> getAllPeliculasDTOByTitulo(String titulo) {
        List<Pelicula> peliculas = buscarPeliculaXTitulo(titulo);
        return this.entity2DTO(peliculas);
    }

    public List<Pelicula> buscarPeliculaXTituloYGenero(String nombre, Integer[] listaGeneros) {
        List<Pelicula> peliculas = new ArrayList<>();
        if (nombre != null && listaGeneros != null) {
            peliculas = peliculasRepository.findByGeneroTitulo(listaGeneros, nombre);
        }
        return peliculas;
    }

    public List<PeliculaDTO> getAllPeliculasDTOByTituloYGenero(String titulo, Integer[] listaGeneros) {
        List<Pelicula> peliculas = buscarPeliculaXTituloYGenero(titulo, listaGeneros);
        return this.entity2DTO(peliculas);
    }

    public Pelicula buscarPelicula(int id) {
        return peliculasRepository.findById(id).orElse(null);
    }

    public PeliculaDTO buscarPeliculaDTO(int id) {
        Pelicula pelicula = buscarPelicula(id);
        return pelicula.toDTO();
    }

    public void eliminarPelicula(Integer id) {
        if (id > 0) {
            peliculasRepository.deleteById(id);
        }
    }

    public List<Pelicula> buscarPeliculaXPopularidad(PageRequest pageable) {
        List<Pelicula> peliculas = new ArrayList<>();
        if (pageable != null && pageable.getPageSize() >= 1) {
            peliculas = peliculasRepository.findTopPeliculasByPopularidad(pageable);
        }

        return peliculas;
    }

    public List<PeliculaDTO> buscarPeliculaDTOByPopularidad(PageRequest pageable) {
        List<Pelicula> peliculas = buscarPeliculaXPopularidad(pageable);
        List<PeliculaDTO> peliculasDTOs = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            peliculasDTOs.add(pelicula.toDTO());
        }

        return peliculasDTOs;
    }

    /**
     * Guarda o actualiza una película a partir de un DTO
     * 
     * @param peliculaDTO DTO de Pelicula a guardar
     * @return El DTO de la película guardada
     */
    public PeliculaDTO guardarPeliculaDTO(PeliculaDTO peliculaDTO) {

        if (peliculaDTO == null) {
            throw new IllegalArgumentException("La peliculaDTO no puede ser nula");
        }

        // Buscar la película existente o crear una nueva
        Pelicula pelicula = peliculaDTO.getId() != null
                ? peliculasRepository.findById(peliculaDTO.getId()).orElse(new Pelicula())
                : new Pelicula();

        // Copiar propiedades básicas del DTO a la entidad
        pelicula.setId(peliculaDTO.getId());
        pelicula.setTitulo(peliculaDTO.getTitulo());
        pelicula.setFechaEstreno(peliculaDTO.getFechaEstreno());
        pelicula.setPresupuesto(peliculaDTO.getPresupuesto());
        pelicula.setIngresos(peliculaDTO.getIngresos());
        pelicula.setDuracion(peliculaDTO.getDuracion());
        pelicula.setDescripcion(peliculaDTO.getDescripcion());
        pelicula.setEnlace(peliculaDTO.getEnlace());
        pelicula.setIdiomaOriginal(peliculaDTO.getIdiomaOriginal());
        pelicula.setPopularidad(peliculaDTO.getPopularidad());
        pelicula.setEstatus(peliculaDTO.getEstatus());
        pelicula.setEslogan(peliculaDTO.getEslogan());
        pelicula.setTitulooriginal(peliculaDTO.getTitulooriginal());
        pelicula.setMediaVotos(peliculaDTO.getMediaVotos());
        pelicula.setNumeroVotos(peliculaDTO.getNumeroVotos());
        pelicula.setPoster(peliculaDTO.getPoster());

        // Mantenemos objetos ligeros como géneros, idiomas y países de producción
        pelicula.setGeneros(peliculaDTO.getGeneros());
        pelicula.setIdiomas(peliculaDTO.getIdiomas());
        pelicula.setPaisproduccions(peliculaDTO.getPaisproduccions());

        // Las relaciones complejas como crews, usuarios, listas y etiquetas
        // deberían manejarse separadamente según la lógica de negocio específica
        // No las actualizamos automáticamente desde el DTO porque necesitarían
        // consultar las entidades completas desde repositorios adicionales

        // Guardar la película actualizada
        Pelicula guardada = peliculasRepository.save(pelicula);
        return guardada.toDTO();
    }
}
