<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.dto.ListaDTO" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/mostrarLista.css">
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

            <form method="post" action="/editarLista?listaId=<%=listaDTO.getId()%>" >
                <button>Editar lista</button>
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
                    <div class="delete-cell"
                         onclick="if (confirm('¿Está seguro de que quiere borrar la película <%= pelicula.getTitulo() %>?')) { window.location.href='/quitarPeliLista?idPeli=<%= pelicula.getId() %>&idLista=<%= listaDTO.getId() %>'; }">
                        ✖
                    </div>
                </td>
            <%}%>
        </tr>

    <%
        }
    %>

    </table>

</body>
<script src="../../js/AñadirAMegusta.js"></script>
</html>
