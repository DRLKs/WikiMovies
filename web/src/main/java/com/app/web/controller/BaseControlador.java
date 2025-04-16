package com.app.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class BaseControlador {
    protected boolean estaAutenticado(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    // Método para verificar las cookies en BaseControlador o donde corresponda
    protected boolean verificarCookieSesion(HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return true; // Ya está autenticado en la sesión
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("wikimovies_user_session".equals(cookie.getName())) {
                    // Aquí puedes validar el ID de sesión o buscar al usuario
                    // según tu estrategia de persistencia
                    return true;
                }
            }
        }
        return false;
    }
}
