<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.web.entity.Genero" %>
<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/index.css">
    <link rel="stylesheet" href="../../css/peliculas.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    List<PeliculaDTO> peliculaList = (List<PeliculaDTO>) request.getAttribute("peliculas");
    List<Genero> generos = (List<Genero>) request.getAttribute("generos");
    List<PeliculaDTO> peliculasTop =(List<PeliculaDTO>) request.getAttribute("peliculasRanking");
    List<Genero> generosFavoritas = (List<Genero>) request.getAttribute("generosFavoritas");
    ListaDTO favoritas = (ListaDTO) request.getAttribute("favoritas");
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

            <div class="ranking-container">                <%
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
                        ⭐ <%= String.format("%.1f", p.getMediaVotos()) %>/10
                    </div>
                </div>

                <%
                        top++;
                    }
                %>

            </div>
        </div>
    </div>

    <% if(usuario != null && usuario.getRol() == 1) {%>
    <div class="peliculas-generos">
        <h3> Peliculas recomendadas en base a tus películas favoritas
        </h3>
        <div class="lista-peliculas">
            <%for (PeliculaDTO p : peliculaList) {%>
            <%if (!generosFavoritas.isEmpty() &&
                    (p.getGeneros().contains(generosFavoritas.get(0)) || (generosFavoritas.size() > 1 && p.getGeneros().contains(generosFavoritas.get(1)))) &&
             !favoritas.getPeliculasId().contains(p.getId())) {%>
            <a href="/film?id=<%= p.getId() %>">
                <img src="<%= p.getPoster() %>"
                     alt="Póster de <%= p.getTitulo() %>"
                     class="poster-ranking"
                     style="width: 180px; height: 240px; object-fit: cover; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.3); transition: transform 0.2s; margin: 5px;"
                >
            </a>
            <%}%>
            <%}%>

        </div>
    </div>
    <% } %>

    <%for (Genero g : generos) {%>
    <%if (!g.getPeliculas().isEmpty()) {%>
    <div class="peliculas-generos">
        <h3> Peliculas de <%=g.getNombre()%>
        </h3>
        <div class="lista-peliculas">
            <%for (PeliculaDTO p : peliculaList) {%>
            <%if (p.getGeneros().contains(g)) {%>
            <a href="/film?id=<%= p.getId() %>">
                <img src="<%= p.getPoster() %>"
                     alt="Póster de <%= p.getTitulo() %>"
                     class="poster-ranking"
                     style="width: 180px; height: 240px; object-fit: cover; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.3); transition: transform 0.2s; margin: 5px;"
                >
            </a>
            <%}%>
            <%}%>

        </div>
    </div>
    <%}%>
    <%}%>


</div>
</body>
</html>