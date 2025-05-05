package com.app.web.controller;

import com.app.web.entity.Usuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseControlador {

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
}
