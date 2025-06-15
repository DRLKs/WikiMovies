<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.dto.ListaDTO" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/mostrarLista.css">
    <link rel="stylesheet" href="../../css/botonesPelicula.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" crossorigin="anonymous"/>
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>
    <%
        ListaDTO listaDTO = (ListaDTO) request.getAttribute("listaDTO");
    %>

<body>

    <%@ include file="barra_navegacion.jsp" %>

    <div class="container-header">
        <div>
            <img src="<%= listaDTO.getFotoUrl() != null ? listaDTO.getFotoUrl() : "../../img/default-list.png" %>"
                 alt="<%= listaDTO.getNombre() %>">
        </div>
        <div class="info">
            <h1 class="lista-name"><%=listaDTO.getNombre()%></h1>
            <p class="lista-descripcion"><%= listaDTO.getDescripcion() %></p>
            <p class="movie-count" > Número de películas: <%=listaDTO.getPeliculas().size()%></p>
        </div>
        <%if( usuario != null && (usuario.getIdUsuario().equals(listaDTO.getUsuarioId()) ) && (!listaDTO.getNombre().equals("Vistas") && !listaDTO.getNombre().equals("Favoritas"))){%>

            <form method="post" action="/editarLista?listaId=<%=listaDTO.getId()%>" style="display:inline-block;">
                <button class="favorite-button" title="Editar lista"><i class="fa fa-pencil"></i></button>
            </form>
            <form method="post" action="/eliminarLista" style="display:inline-block; margin-left:8px;" onsubmit="return confirm('¿Seguro que quieres eliminar esta lista? Esta acción no se puede deshacer.');">
                <input type="hidden" name="listaId" value="<%=listaDTO.getId()%>" />
                <button class="favorite-button delete-btn" title="Eliminar lista"><i class="fa fa-trash"></i></button>
            </form>

        <%}%>

    </div>    
    
    <table>
        <tr>
            <td> Nombre Películas </td>
            <td> Duración </td>
            <%if(usuario != null && listaDTO.getUsuarioId().equals(usuario.getIdUsuario())){%>
            <td></td>
            <%}%>
        </tr>    <%
        for(PeliculaDTO pelicula : listaDTO.getPeliculas()){
    %>
        <tr>
            <td>
                <div class="clickable" onclick="window.location.href='film?id=<%= pelicula.getId() %>';">
                    <%= pelicula.getTitulo() %>
                </div>
            </td>
            <td>
                <div class="clickable" onclick="window.location.href='film?id=<%= pelicula.getId() %>';">
                    <%= pelicula.getDuracion() %>
                </div>
            </td>
            <%if(usuario != null && listaDTO.getUsuarioId().equals(usuario.getIdUsuario())){%>
                <td class="delete-cell-wrapper">
                    <button class="favorite-button delete-btn" title="Eliminar película de la lista"
                        onclick="if (confirm('¿Está seguro de que quiere borrar la película <%= pelicula.getTitulo() %>?')) { window.location.href='/quitarPeliLista?idPeli=<%= pelicula.getId() %>&idLista=<%= listaDTO.getId() %>'; } return false;">
                        <i class="fa fa-times"></i>
                    </button>
                </td>
            <%}%>
        </tr>

    <%
        }
    %>

    </table>

</body>
</html>