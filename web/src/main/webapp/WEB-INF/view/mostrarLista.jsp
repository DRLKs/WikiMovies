<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page import="com.app.web.entity.Usuario" %>
<%@ page import="com.app.web.service.PeliculasService" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/mostrarLista.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>
    <%
        Usuario usuario2 = (Usuario) request.getAttribute("usuario");
        ListaDTO listaDTO = (ListaDTO) request.getAttribute("listaDTO");
        PeliculasService peliculasService = (PeliculasService) request.getAttribute("peliculasService");
        Boolean peliculaFavorita = (Boolean) request.getAttribute("peliculaFavorita");
    %>

<body>

    <%@ include file="barra_navegacion.jsp" %>    <div class="container-header">
        <div>
            <img src="<%= listaDTO.getFotoUrl() != null ? listaDTO.getFotoUrl() : "../../img/default-list.png" %>"
                 alt="<%= listaDTO.getNombre() %>">
        </div>
        <div class="info">
            <h1 class="lista-name"><%=listaDTO.getNombre()%></h1>
            <p class="lista-descripcion"><%= listaDTO.getDescripcion() %></p>
            <p class="movie-count" > Número de películas: <%=listaDTO.getPeliculasId().size()%></p>
        </div>        <%if((usuario2 != null) && (!listaDTO.getNombre().equals("Vistas") && !listaDTO.getNombre().equals("Favoritas"))){%>
            <form method="post" action="/editarLista?listaId=<%=listaDTO.getId()%>" >
                <button>Editar lista</button>
            </form>

        <form action="/favorite" method="post" class="favorite-form">
            <input type="hidden" name="idPelicula" value="<%= listaDTO.getId() %>">
            <button type="submit" class="favorite-button">
                <i class="heart-icon <%= peliculaFavorita ? "active" : "" %>">❤</i>
            </button>
        </form>
        <%}%>

    </div>    <table>
        <tr>
            <td> Nombre Películas </td>
            <td> Duración </td>
            <%if(usuario2 != null){%>
            <td></td>
            <%}%>
        </tr>    <%
        for(Integer peliculaId : listaDTO.getPeliculasId()){
            PeliculaDTO pelicula = peliculasService.buscarPeliculaDTO(peliculaId);
            if(pelicula != null) {
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
            <%if(usuario2 != null){%>
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
        }
    %>

    </table>

</body>
<script src="../../js/AñadirAMegusta.js"></script>
</html>
