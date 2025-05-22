<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.entity.Lista" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/mostrarLista.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>


    <%
        Usuario usuario2 = (Usuario) request.getAttribute("usuario");
        Lista lista = (Lista) request.getAttribute("lista");
    %>

<body>

    <%@ include file="barra_navegacion.jsp" %>

    <div class="container-header">
        <div>
            <img src="<%= lista.getImgURL() != null ? lista.getImgURL() : "../../img/default-list.png" %>"
                 alt="<%= lista.getNombre() %>">
        </div>
        <div class="info">
            <h1 class="lista-name"><%=lista.getNombre()%></h1>
            <p class="lista-descripcion"><%= lista.getDescripcion() %></p>
            <p class="movie-count" > Número de películas: <%=lista.getPeliculas().size()%></p>
        </div>
        <%if((usuario2 != null && usuario2.getId() == lista.getIdUsuario().getId()) && (!lista.getNombre().equals("Vistas")  && !lista.getNombre().equals("Favoritas"))){%>
            <form method="post" action="/editarLista?listaId=<%=lista.getId()%>" >
                <button>Editar lista</button>
            </form>

        <form action="/favorite" method="post" class="favorite-form">
            <input type="hidden" name="idPelicula" value="<%= lista.getId() %>">
            <button type="submit" class="favorite-button">
                <i class="heart-icon <%= peliculaFavorita ? "active" : "" %>">❤</i>
            </button>
        </form>
        <%}%>

    </div>

    <table>
        <tr>
            <td> Nombre Películas </td>
            <td> Duración </td>
            <%if(usuario2 != null && usuario2.getId() == lista.getIdUsuario().getId()){%>
            <td></td>
            <%}%>
        </tr>
    <%
        for( Pelicula pelicula : lista.getPeliculas() ){
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
            <%if(usuario2 != null && usuario2.getId() == lista.getIdUsuario().getId()){%>
                <td class="delete-cell-wrapper">
                    <div class="delete-cell"
                         onclick="if (confirm('¿Está seguro de que quiere borrar la película <%= pelicula.getTitulo() %>?')) { window.location.href='/quitarPeliLista?idPeli=<%= pelicula.getId() %>&idLista=<%= lista.getId() %>'; }">
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
