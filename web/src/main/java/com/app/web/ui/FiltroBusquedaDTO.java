package com.app.web.ui;

import lombok.Data;

@Data
public class FiltroBusquedaDTO {

    /**
     * Filtro para buscar películas por el títlo
     */
    private String titulo;

    /**
     * Lista con los identificadores de los generos para filtrar
     */
    private Integer[] generos;
}
