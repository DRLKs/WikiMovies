<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.web.dto.GeneroDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/index.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" crossorigin="anonymous"/>
</head>

<%
    List<PeliculaDTO> peliculaList = (List<PeliculaDTO>) request.getAttribute("peliculas");
    List<GeneroDTO> generos = (List<GeneroDTO>) request.getAttribute("generos");
    List<PeliculaDTO> peliculasTop =(List<PeliculaDTO>) request.getAttribute("peliculasRanking");
    List<PeliculaDTO> peliculasRecomendadasFavoritas =(List<PeliculaDTO>) request.getAttribute("peliculasRecomendadasFavoritas");
    List<PeliculaDTO> peliculasRecomendadasVistas =(List<PeliculaDTO>) request.getAttribute("peliculasRecomendadasVistas");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<%
    if(usuario != null && usuario.getRol() == 2) {
%>
    <a href="/crearPelicula" class="crear-pelicula-btn">CREAR PELICULA</a>
<%
    }
%>

<div class="peliculas-container">
    <div class="peliculas-recomendadas">
        <div class="container">

            <h4 class="ranking-titulo">ESTA ES LA SELECCIÓN DEL DÍA DE HOY:</h4>

            <div class="ranking-container"><%
                int top = 1;
                for (int i = 0; i < peliculasTop.size(); i++) {
                    PeliculaDTO p = peliculasTop.get(i);
            %>
                <div class="pelicula-ranking">
                    <div class="ranking-etiqueta <%= top <= 3 ? "podio-" + top : "" %>">TOP <%= top %></div>
                    <h4><%= p.getTitulo() %></h4>
                    <a href="film?id=<%= p.getId() %>">
                        <img src="<%= p.getPoster() %>" alt="Póster de <%= p.getTitulo() %>" class="poster-ranking">
                    </a>
                    <div class="puntuacion">
                        ⭐ <%= String.format("%.1f", p.getPopularidad()/10) %>/10
                    </div>
                </div>

                <%
                        top++;
                    }
                %>

            </div>
        </div>
    </div>

    <% if(usuario != null && usuario.getRol() >= 1 && peliculasRecomendadasFavoritas != null && !peliculasRecomendadasFavoritas.isEmpty()) {%>
    <div class="peliculas-generos">
        <h3 class="titulo-peliculas-recomendadas"> Películas recomendadas en base a tus películas favoritas
        </h3>
        <div class="lista-peliculas-wrapper">
            <button class="scroll-btn scroll-btn-left" onclick="scrollLista(this, -1)"><i class="fa fa-chevron-left"></i></button>
            <div class="lista-peliculas">
                <%for (PeliculaDTO p : peliculasRecomendadasFavoritas) {%>
                <a href="/film?id=<%= p.getId() %>">
                    <img src="<%= p.getPoster() %>"
                         alt="Póster de <%= p.getTitulo() %>"
                         class="poster-ranking"
                    >
                </a>
                <%}%>
            </div>
            <button class="scroll-btn scroll-btn-right" onclick="scrollLista(this, 1)"><i class="fa fa-chevron-right"></i></button>
        </div>
    </div>
    <%}%>

    <% if(usuario != null && usuario.getRol() >= 1 && peliculasRecomendadasVistas != null && !peliculasRecomendadasVistas.isEmpty()) {%>
    <div class="peliculas-generos">
        <h3 class="titulo-peliculas-recomendadas"> Películas recomendadas en base a tus películas vistas
        </h3>
        <div class="lista-peliculas-wrapper">
            <button class="scroll-btn scroll-btn-left" onclick="scrollLista(this, -1)"><i class="fa fa-chevron-left"></i></button>
            <div class="lista-peliculas">
                <%for (PeliculaDTO p : peliculasRecomendadasVistas) {%>
                <a href="/film?id=<%= p.getId() %>">
                    <img src="<%= p.getPoster() %>"
                         alt="Póster de <%= p.getTitulo() %>"
                         class="poster-ranking"
                    >
                </a>
                <%}%>
            </div>
            <button class="scroll-btn scroll-btn-right" onclick="scrollLista(this, 1)"><i class="fa fa-chevron-right"></i></button>
        </div>
    </div>
    <%}%>

    <%for (GeneroDTO g : generos) {%>
    <%if (!g.getPeliculasIDs().isEmpty()) {%>
    <div class="peliculas-generos">
        <h3> Películas de <%=g.getNombre()%>
        </h3>
        <div class="lista-peliculas-wrapper">
            <button class="scroll-btn scroll-btn-left" onclick="scrollLista(this, -1)"><i class="fa fa-chevron-left"></i></button>
            <div class="lista-peliculas">
                <%for (PeliculaDTO p : peliculaList) {%>
                <%if (g.getPeliculasIDs().contains(p.getId())) {%>
                <a href="/film?id=<%= p.getId() %>">
                    <img src="<%= p.getPoster() %>"
                         alt="Póster de <%= p.getTitulo() %>"
                         class="poster-ranking"
                    >
                </a>
                <%}%>
                <%}%>
            </div>
            <button class="scroll-btn scroll-btn-right" onclick="scrollLista(this, 1)"><i class="fa fa-chevron-right"></i></button>
        </div>
    </div>
    <%}%>
    <%}%>


</div>
<script src="../../js/index.js"></script>
</body>
</html>