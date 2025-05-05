<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/search.css">

<%
    String titulo = (String) request.getAttribute("titulo");
    List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
    Boolean peliculaFavorita;
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

    <div class="info-busqueda-container" >
        <h2> Mostrando <%= peliculas.size() %> resultados para "<%=titulo%>"</h2> <br>
        ----------------------------------------
    </div>

    <div class="peliculas-container">

        <%
            if ( peliculas.isEmpty() ){
        %>
            <h1> No hay películas con este filtro </h1>
        <%
            }
        %>

        <%
            for ( Pelicula pelicula : peliculas ){
               peliculaFavorita = pelicula.peliculaEsFavoritaPorUsuario(usuario);
        %>

        <a href="film?id=<%= pelicula.getId() %>" class="pelicula-link">
            <div class="pelicula-container">
                <div class="pancarta-container">
                    <img src="<%= pelicula.getPoster() %>" alt="Pancarta Pelicula">
                </div>
                <div class="informacion-container">
                    <div class="informacion-basica-container">
                        <h3> <%= pelicula.getTitulo() + " (" + pelicula.getFechaEstreno().getYear() + ")"%> </h3>
                        Nota por la crítica: <%= pelicula.getMediaVotos()%>
                    </div>
                    <div class="descripcion-container">
                        <%= pelicula.getDescripcion()%>
                    </div>
                </div>
                <form action="/favorite" method="post" class="favorite-form" >
                    <input type="hidden" name="id" value="<%= pelicula.getId() %>">
                    <button type="submit" class="favorite-button">
                        <i class="heart-icon <%= peliculaFavorita ? "active" : "" %>">❤</i>
                    </button>
                </form>
            </div>
        </a>
        <%  } %>
    </div>

<script src="../../js/login.js" > </script>

</body>
</html>