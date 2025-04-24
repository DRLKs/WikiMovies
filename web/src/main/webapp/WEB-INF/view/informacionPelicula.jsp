<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.app.web.entity.Pelicula" %>
<%
    Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
%>
<html>
<head>
    <title><%= pelicula.getTitulo() %> - Detalles</title>

    <link rel="stylesheet" href="../../css/informacionPelicula.css">
</head>
<body>
<div class="detalle-container">
    <div class="poster-box">
        <img src="<%= pelicula.getPoster() %>" alt="Póster de <%= pelicula.getTitulo() %>">
    </div>
    <div class="info-box">
        <h1><%= pelicula.getTitulo() %></h1>
        <p class="subtitulo"><%= pelicula.getEslogan() != null ? pelicula.getEslogan() : "" %></p>

        <div class="campo"><strong>Título original:</strong> <%= pelicula.getTitulooriginal() %></div>
        <div class="campo"><strong>Fecha de estreno:</strong> <%= pelicula.getFechaEstreno() %></div>
        <div class="campo"><strong>Duración:</strong> <%= pelicula.getDuracion() %> min</div>
        <div class="campo"><strong>Idioma original (ID):</strong> <%= pelicula.getIdiomaOriginal() %></div>
        <div class="campo"><strong>Popularidad:</strong> <%= pelicula.getPopularidad() %></div>
        <div class="campo"><strong>Estado:</strong> <%= pelicula.getEstatus() %></div>
        <div class="campo"><strong>Presupuesto:</strong> $<%= pelicula.getPresupuesto() %></div>
        <div class="campo"><strong>Ingresos:</strong> $<%= pelicula.getIngresos() %></div>
        <div class="campo"><strong>Media de votos:</strong> ⭐ <%= pelicula.getMediaVotos() %> / 10</div>
        <div class="campo"><strong>Número de votos:</strong> <%= pelicula.getNumeroVotos() %></div>
        <div class="campo descripcion">
            <strong>Descripción:</strong>
            <p><%= pelicula.getDescripcion() %></p>
        </div>
        <div class="campo enlace">
            <a href="<%= pelicula.getEnlace() %>" target="_blank">Ver más información</a>
        </div>
    </div>
</div>
</body>
</html>
