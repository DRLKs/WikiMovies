package com.app.web.ui;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioProfile {

    /**
     *
     */
    protected Integer id;

    /**
     * Nombre de usuario
     */
    protected String nombreUsuario;

    /**
     * URL de la foto de perfil del usuario
     */
    protected String avatar;

    /**
     * Biografía del usuario
     */
    protected String biografia;

    /**
     * Entero que identifica el género del usuario
     * <ul>
     *   <li><b>1</b> - Hombre</li>
     *   <li><b>2</b> - Mujer</li>
     *   <li><b>3</b> - Otro</li>
     * </ul>
     */
    protected Integer genero;

    /**
     * Fecha de nacimiento del usuario
     */
    protected LocalDate fechaNacimiento;

    /**
     *
     */
    protected Integer rol;
}
