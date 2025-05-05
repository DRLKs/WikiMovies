package com.app.web.ui;

import lombok.Data;

@Data
public class UsuarioSignup {

    /**
     * Nombre de usuario
     */
    protected String username;

    /**
     * Correo electrónico
     */
    protected String email;

    /**
     * Contraseña del usuario
     */
    protected String password;

    /**
     * Confirmación de contraseña, esta debe ser igual que {@link #password}
     */
    protected String passwordConfirmed;
}
