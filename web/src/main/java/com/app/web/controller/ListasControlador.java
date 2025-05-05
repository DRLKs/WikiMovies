package com.app.web.controller;

import com.app.web.dao.GenerosRepository;
import com.app.web.dao.ListaRepository;
import com.app.web.dao.PeliculasRepositorio;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Genero;
import com.app.web.entity.Lista;
import com.app.web.entity.Pelicula;
import com.app.web.entity.Usuario;
import com.app.web.ui.FiltroBusquedaDTO;
import com.app.web.ui.NuevaLista;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.app.web.utils.Constantes.USUARIO_SESION;

@Controller
public class ListasControlador extends BaseControlador{

    @Autowired
    protected GenerosRepository generosRepositorio;
    @Autowired
    protected PeliculasRepositorio peliculasRepositorio;
    @Autowired
    protected UsuariosRepositorio usuarioRepositorio;
    @Autowired
    protected ListaRepository listaRepository;

    /**
     * Controlador de la petición del sistema para cargar todas las
     */
    @GetMapping("/listas")
    public String mostrarListas(Model model, HttpServletRequest request, HttpSession session) {
        Usuario usuario = null;
        // Obtenemos los datos del usuario
        if(estaAutenticado(request,session) ) {
            int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
            usuario = usuarioRepositorio.getUsuarioById(idUsuario);
        }


        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("usuario", usuario);

        return "listas";
    }

    @GetMapping("/crearLista")
    public String crearLista(Model model, HttpServletRequest request, HttpSession session) {
        // Un usuario no puede guardarse una películas como favorita si tiene la sesión iniciada
        if( !estaAutenticado(request,session) ) {
            return "redirect:/login";
        }

        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioRepositorio.getUsuarioById(idUsuario);

        List<Pelicula> peliculas = peliculasRepositorio.findAll();
        model.addAttribute("peliculas", peliculas);
        List<Genero> generos = generosRepositorio.findAll();
        model.addAttribute("generos", generos);
        model.addAttribute("filtroBusquedaDTO", new FiltroBusquedaDTO());
        model.addAttribute("usuario", usuario);
        model.addAttribute("nuevaLista", new NuevaLista());

        return "crearLista";
    }

    @PostMapping("/guardarLista")
    public String guardarLista(@ModelAttribute("nuevaLista") NuevaLista nuevaLista, Model model, HttpServletRequest request, HttpSession session) {

        int idUsuario = ((Usuario) session.getAttribute(USUARIO_SESION)).getId();
        Usuario usuario = usuarioRepositorio.getUsuarioById(idUsuario);

        Lista lista = new Lista();
        lista.setNombre(nuevaLista.getNombre());
        //añadir descripcion en la bbdd y aqui
        lista.setIdUsuario(usuario);
        Set<Pelicula> pelis = new HashSet<>();
        for(Integer peliId : nuevaLista.getPeliculasId()){
            Pelicula aux =  peliculasRepositorio.getPeliculaById(peliId);
            pelis.add(aux);
        }
        lista.setPeliculas(pelis);

        listaRepository.save(lista);

        return "redirect:/";
    }

}
