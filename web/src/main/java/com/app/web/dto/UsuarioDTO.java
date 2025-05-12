package com.app.web.dto;

import lombok.Data;

@Data
public class UsuarioDTO{

    /**
     * Identificador del usuario
     */
    protected Integer idUsuario;

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


}
