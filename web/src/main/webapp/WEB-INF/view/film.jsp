<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="com.app.web.entity.Genero" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/film.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" crossorigin="anonymous"/>
</head>

<%
    boolean peliculaFavorita = (boolean) request.getAttribute("peliculaFavorita");
    boolean peliculaVista = (boolean) request.getAttribute("peliculaVista");
    Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="pelicula-content">
    <div class="poster-container">
        <img src="<%= pelicula.getPoster() %>" alt="No tenemos imagen para esta película">
    </div>
    <div class="informacion-container">
        <h3><%= pelicula.getTitulo() + "   (" + pelicula.getFechaEstreno().getYear() + ")" %></h3>
        <i class="eslogan"><%= pelicula.getEslogan() %></i>

        <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "VISTAS" -->
        <form action="/seen" method="post" class="seen-form">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button">
                <i class="<%= peliculaVista ? "fas fa-eye" : "fas fa-eye-slash" %>"></i>
            </button>
        </form>

        <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "FAVORITAS" -->
        <form action="/favorite" method="post" class="favorite-form">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button">
                <i class="heart-icon <%= peliculaFavorita ? "active" : "" %>">❤</i>
            </button>
        </form>

        <div class="descripcion-container">
            <%= pelicula.getDescripcion() %>
        </div>

        <div class="duration">
            <%= "⏳" + pelicula.getDuracion() + " mins" %>
        </div>

        <%
            if (pelicula.getGeneros() != null && !pelicula.getGeneros().isEmpty()) {
        %>
        <div class="generosTags-container">
            <%
                for (Genero genero : pelicula.getGeneros()) {
            %>
            <span class="genero-tag"><%= genero.getNombre() %></span>
            <%
                }
            %>
        </div>
        <%
            }
        %>

    </div>
</div>


</body>
</html>