<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.web.entity.Genero" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/index.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    List<Pelicula> peliculaList = (List<Pelicula>) request.getAttribute("peliculas");
    List<Genero> generos = (List<Genero>) request.getAttribute("generos");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="peliculas-container">
    <div class="peliculas-recomendadas">
        <h3> ¿Qué te apetece ver hoy? </h3>
        <div class="lista-peliculas">

            <%
                for (Pelicula p : peliculaList) {
            %>
            <a href="/film?id=<%= p.getId() %>">
                <img src="<%= p.getPoster() %>"
                     alt="Cartel de <%= p.getTitulo() %>"
                     title="<%= p.getTitulo() %>"
                     class="poster-ranking"
                >
            </a>
            <!-- ¿Agregar botones de añadir a favoritas/vistas? -->


            <%
                }
            %>


        </div>
    </div>
    <%for (Genero g : generos) {%>
    <div class="peliculas-generos">
        <h3> Peliculas de <%=g.getNombre()%>
        </h3>
        <div class="lista-peliculas">
            <%for (Pelicula p : peliculaList) {%>
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


</div>
</body>
</html>