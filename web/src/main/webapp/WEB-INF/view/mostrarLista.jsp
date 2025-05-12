<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.entity.Lista" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/mostrarLista.css">

    <%
        Usuario usuarioLista = (Usuario) request.getAttribute("usuarioLista");
        Lista lista = (Lista) request.getAttribute("lista");
    %>

<body>

    <%@ include file="barra_navegacion.jsp" %>

    <div class="container-header">
        <div>
            <img src="<%=null != null ? "lista.imagenUrl" : "../../img/default-list.png"%>" alt="<%= lista.getNombre() %>">
        </div>
        <div class="info">
            <h1 class="lista-name"><%=lista.getNombre()%></h1>
            <p class="lista-descripcion"><%= lista.getDescripcion() %></p>
            <p class="movie-count" > Número de películas: <%=lista.getPeliculas().size()%></p>
        </div>


    </div>

    <table>

        <tr>
            <td> Nombre Películas </td>
            <td> Duración </td>
            <td></td>
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
            <td class="delete-cell-wrapper">
                <div class="delete-cell" onclick="window.location.href='/quitarPeliLista?idPeli=<%= pelicula.getId() %>&idLista=<%= lista.getId() %>';">
                    ✖
                </div>
            </td>
        </tr>

    <%
        }
    %>
    </table>

</body>
</html>
