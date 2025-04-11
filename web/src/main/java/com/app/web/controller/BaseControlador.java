package com.app.web.controller;

import jakarta.servlet.http.HttpSession;

public class BaseControlador {
    protected boolean estaAutenticado(HttpSession session) {
        return session.getAttribute("user") != null;
    }
}
