package com.app.web.service;

import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Lista;
import com.app.web.entity.Usuario;
import com.app.web.utils.Hash;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.app.web.utils.Constantes.*;

public class UsuarioService {

    @Autowired private UsuariosRepositorio usuarioRepositorio;
    @Autowired private ListasService listasService;

    // Mapa simple para cachear sesiones (en producción usar algo más robusto como Redis)
    private static final Map<String, Usuario> sesionesCache = new ConcurrentHashMap<>();

    protected boolean estaAutenticado(HttpServletRequest request, HttpSession session) {

        boolean autenticado = false;
        if( session.getAttribute("usuario") != null ) {
            autenticado =  true;
        }else{
            Usuario usuario = verificarCookieSesion(request);

            if( usuario != null ) {
                session.setAttribute("usuario", usuario);
                autenticado = true;
            }
        }

        return autenticado;
    }

    protected Usuario verificarCookieSesion(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("wikimovies_user_session".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    // Si no hay usuario en la sesión, buscamos en el caché por el token
                    Usuario usuarioCached = obtenerUsuarioDesdeSesionCache(token);
                    if (usuarioCached != null) {
                        // Si encontramos al usuario, lo guardamos en la sesión actual
                        // para futuras solicitudes
                        return usuarioCached;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Método para guardar un usuario en el cache de sesiones
     * @param token Identificador del usuario en la caché
     * @param usuario Usuario a guardar
     */
    protected void guardarUsuarioEnSesionCache(String token, Usuario usuario) {
        sesionesCache.put(token, usuario);
    }

    /**
     * Método para eliminar un usuario del cache de sesiones
     * @param token Identificador del usuario en la caché
     */
    protected void eliminarUsuarioDelSesionCache(String token) {
        sesionesCache.remove(token);
    }

    /**
     * Método para recuperar usuario desde el cache de sesiones
     * @param token Identificador del usuario en la caché
     * @return Usuario obtenido en la caché
     */
    private Usuario obtenerUsuarioDesdeSesionCache(String token) {
        return sesionesCache.get(token);
    }

    public String crearCuenta(String nombreUsuario, String correoElectronico, String contrasena,
                                Model model) {
        if( this.usuarioRepositorio.existsByNombreUsuario(nombreUsuario) ){
            String msg = "El usuario indicado ya está en eso";
            model.addAttribute("mensaje", msg);
            return "signup";
        }

        if( this.usuarioRepositorio.existsBycorreoElectronico(correoElectronico) ){
            String msg = "El email indicado ya está registrado";
            model.addAttribute("mensaje", msg);
            return "signup";
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setContrasenaHash( Hash.obtenerSHA256(contrasena) );
        usuario.setBiografia(""); //añadido
        usuario.setCreacionCuentaFecha(LocalDate.now());
        usuario.setRol(0);      // El rol 0, indica el rol "normal"

        this.usuarioRepositorio.save(usuario);


        // Creamos el conjunto donde se almacenarán las listas del usuario
        Set<Lista> listas = new HashSet<>();

        //Creamos la lista que conteine las películas favoritas del usuario
        Lista listaFav = listasService.nuevaLista(LISTA_FAVORITAS,DESCRIPCION_LISTA_FAVORITAS,IMAGEN_LISTA_FAVORITAS, usuario);
        listas.add(listaFav);

        // Creamos la lista que contiene las películas vistas
        Lista listaVistas = listasService.nuevaLista(LISTA_VISTAS,DESCRIPCION_LISTA_VISTAS,IMAGEN_LISTA_VISTAS, usuario);
        listas.add((listaVistas));

        this.listasService.guardarLista(listaVistas);
        this.listasService.guardarLista(listaFav);

        usuario.setListas(listas);
        this.usuarioRepositorio.save(usuario);

        return "redirect:/login";
    }

    public Usuario buscarUsuario(Integer idUsuario) {
        Usuario usuario = null;
        if(idUsuario > 0){
            usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        }
        return usuario;
    }

    public List<Usuario> usuarioSeguidos(int id){
        List<Usuario> seguidos = new ArrayList<>();
        if(id > 0){
            seguidos = usuarioRepositorio.getSeguidos(id);
        }
        return seguidos;
    }


}
