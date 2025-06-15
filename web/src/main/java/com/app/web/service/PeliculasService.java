package com.app.web.service;

import com.app.web.dao.GenerosRepository;
import com.app.web.dao.IdiomasRepository;
import com.app.web.dao.PeliculasRepository;
import com.app.web.dto.GeneroDTO;
import com.app.web.dto.IdiomaDTO;
import com.app.web.dto.PeliculaDTO;
import com.app.web.entity.Genero;
import com.app.web.entity.Idioma;
import com.app.web.entity.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PeliculasService extends DTOService<PeliculaDTO, Pelicula> {

    @Autowired private PeliculasRepository peliculasRepository;
    @Autowired private IdiomasRepository idiomasRepository;
    @Autowired private GenerosRepository generosRepository;

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
        pelicula.setIdiomaOriginal( idiomasRepository.getReferenceById( peliculaDTO.getIdiomaOriginal().getId() ) );
        pelicula.setPopularidad(peliculaDTO.getPopularidad());
        pelicula.setEstatus(peliculaDTO.getEstatus());
        pelicula.setEslogan(peliculaDTO.getEslogan());
        pelicula.setTitulooriginal(peliculaDTO.getTitulooriginal());
        pelicula.setMediaVotos(peliculaDTO.getMediaVotos());
        pelicula.setNumeroVotos(peliculaDTO.getNumeroVotos());
        pelicula.setPoster(peliculaDTO.getPoster());

        // Obtenemos los géneros
        Set<Genero> generos = new HashSet<>();
        for( GeneroDTO generoDTO : peliculaDTO.getGeneros() ){
            generos.add( generosRepository.getReferenceById(generoDTO.getId()) );
        }
        pelicula.setGeneros(generos);

        // Obtenemos los idiomas
        Set<Idioma> idiomas = new HashSet<>();
        for( IdiomaDTO idiomaDTO : peliculaDTO.getIdiomas() ){
            idiomas.add( idiomasRepository.getReferenceById(idiomaDTO.getId()) );
        }
        pelicula.setIdiomas(idiomas);

        pelicula.setPaisproduccions(peliculaDTO.getPaisproduccions());

        // Las relaciones complejas como crews, usuarios, listas y etiquetas
        // deberían manejarse separadamente según la lógica de negocio específica
        // No las actualizamos automáticamente desde el DTO porque necesitarían
        // consultar las entidades completas desde repositorios adicionales

        // Guardar la película actualizada
        Pelicula guardada = peliculasRepository.save(pelicula);
        return guardada.toDTO();
    }

    /**
     * Películas que recomendaremos al usuario según sus géneros más vistos.
     * @param peliculasUsuario Lista de películas que el usuario ya tiene en su lista
     * @param genero1 Primer género
     * @param genero2 Segundo género
     * @return Lista de películas que contienen alguno de los géneros
     */
    public List<PeliculaDTO> filtrarPorDosGeneros(List<PeliculaDTO> peliculasUsuario, GeneroDTO genero1, GeneroDTO genero2) {

        List<Pelicula> peliculasGeneros;

        // Para los casos en el que usuario solo le gusten películas con un género
        // La función se reutiliza pero pasando un género como nulo
        if( genero1 == null ) {
            peliculasGeneros = peliculasRepository.getPeliculasByGenero(genero2.getId());
        }else if( genero2 == null ) {
            peliculasGeneros = peliculasRepository.getPeliculasByGenero(genero1.getId());
        }else{
            peliculasGeneros = peliculasRepository.getPeliculasByGeneros(genero1.getId(), genero2.getId());
        }

        List<PeliculaDTO> peliculasObtenidas = new ArrayList<>();

        // Recorremos todas las películas que contienen esos géneros
        for( PeliculaDTO pelicula : entity2DTO(peliculasGeneros) ){
            if( !peliculasUsuario.contains(pelicula) ){
                peliculasObtenidas.add(pelicula);
            }
        }

        return peliculasObtenidas;
    }

}
