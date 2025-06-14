package com.app.web.ui;

import lombok.Data;

@Data
public class UsuarioLogin {

    /**
     * Correo electrónico del usuario
     */
    protected String email;

    /**
     * Cadena de caracteres que compone la contraseña del usuario
     */
    protected String password;

}
