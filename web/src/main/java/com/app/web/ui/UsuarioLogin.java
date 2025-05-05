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

    /**
     * Booleano que indica si la sesión debe ser guardada o no
     * @True Se guarda la sesión
     * @False No se guarda la sesión
     */
    protected Boolean remember;
}
