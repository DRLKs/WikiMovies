<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="com.app.web.entity.Lista" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/search.css">
    <link rel="stylesheet" href="../../css/botonesPelicula.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
          crossorigin="anonymous"/>
</head>

<%
    String titulo = (String) request.getAttribute("titulo");
    List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
    Lista favoritas = (Lista) request.getAttribute("favoritas");
    Lista vistas = (Lista) request.getAttribute("vistas");
    boolean peliculaFavorita = false;
    boolean peliculaVista = false;
    List<Lista> listasUsuario = (List<Lista>) request.getAttribute("listasUsuario");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="info-busqueda-container">
    <h2> Mostrando <%= peliculas.size() %> resultados para "<%=titulo%>"</h2> <br>
    ----------------------------------------
</div>

<div class="peliculas-container">

    <%
        if (peliculas.isEmpty()) {
    %>
    <h1> No hay películas con este filtro </h1>
    <%
        }

        for (Pelicula pelicula : peliculas) {
            if (vistas != null) {
                peliculaVista = vistas.getPeliculas().contains(pelicula);
            }
            if (favoritas != null) {
                peliculaFavorita = favoritas.getPeliculas().contains(pelicula);
            }
    %>

    <a href="film?id=<%= pelicula.getId() %>" class="pelicula-link">
        <div class="pelicula-container">

            <!-- SE MUESTRA EL POSTER DE LA PELICULA -->
            <div class="pancarta-container">
                <img src="<%= pelicula.getPoster() %>" alt="Pancarta Pelicula">
            </div>
            <!---------------------------------------------------------->

            <!-- SE MUESTRA LA INFO BASICA DE LA PELI -->
            <div class="informacion-container">
                <div class="informacion-basica-container">
                    <h3><%= pelicula.getTitulo() + " (" + pelicula.getFechaEstreno().getYear() + ")"%>
                    </h3>
                    Nota por la crítica: <%= pelicula.getMediaVotos()%>
                </div>
                <div class="descripcion-container">
                    <%= pelicula.getDescripcion()%>
                </div>
            </div>
            <!---------------------------------------------------------->
            <!-- BOTON DE AÑADIR PELICULA A UNA LISTA -->


            <!---------------------------------------------------------->

            <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "VISTAS" -->
            <form action="/seen" method="post" class="seen-form">
                <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
                <button type="submit" class="favorite-button">
                    <i class="<%= peliculaVista ? "fas fa-eye" : "fas fa-eye-slash" %>"></i>
                </button>
            </form>
            <!---------------------------------------------------------->

            <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "FAVORITAS" -->
            <form action="/favorite" method="post" class="favorite-form">
                <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
                <button type="submit" class="favorite-button">
                    <i class="heart-icon <%= peliculaFavorita ? "active" : "" %>">❤</i>
                </button>
            </form>
            <!---------------------------------------------------------->

        </div>
    </a>
    <% } %>
</div>

<script src="../../js/login.js"></script>
<script src="../../js/addToList.js"></script>

</body>
</html>