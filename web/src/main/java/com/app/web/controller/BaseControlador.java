package com.app.web.controller;

import jakarta.servlet.http.HttpSession;

import static com.app.web.utils.Constantes.USUARIO_SESION;

public class BaseControlador {


    protected boolean estaAutenticado(HttpSession session) {

        return session.getAttribute(USUARIO_SESION) != null;
    }


}
