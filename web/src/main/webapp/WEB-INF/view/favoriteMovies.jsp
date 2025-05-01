<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/favoriteMovies.css">

    <%
        Usuario usuarioLista = (Usuario) request.getAttribute("usuarioLista");
        Set<Pelicula> peliculasFavoritas = (Set<Pelicula>) request.getAttribute("peliculasFavoritas");
    %>

<body>

    <%@ include file="barra_navegacion.jsp" %>

    <table>

        <caption> Películas Favoritas de <%= usuarioLista.getNombreUsuario()  %> </caption>
        <tr>
            <td> Nombre Películas </td>
            <td> Duración </td>
        </tr>
    <%
        for( Pelicula pelicula : peliculasFavoritas ){
    %>
        <tr>
            <td> <%= pelicula.getTitulo() %> </td>
            <td> <%= pelicula.getDuracion() %> </td>
        </tr>

    <%
        }
    %>
    </table>

</body>
</html>
