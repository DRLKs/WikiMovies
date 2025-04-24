<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/index.css">

<%
    List<Pelicula> peliculaList = (List<Pelicula>) request.getAttribute("peliculas");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="peliculas-container">
    <div class="peliculas-recomendadas">
        <h3> ¿Qué te apetece ver hoy? </h3>
        <div class="lista-peliculas">

            <%
                for (Pelicula pelicula : peliculaList) {
            %>
            <img
                    src="<%= pelicula.getPoster() %>"
                    alt="Pancarta Pelicula"
                    style="width: 180px; height: 240px; object-fit: cover; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.3); transition: transform 0.2s; margin: 5px;"
            >
            <%
                }
            %>

        </div>
    </div>
    <div class="peliculas-terror">
        <h3> Peliculas de terror</h3>
        <div class="lista-peliculas">

        </div>
    </div>
    <div class="peliculas-humor">
        <h3> Peliculas de humor</h3>
        <div class="lista-peliculas">

        </div>
    </div>
</div>
</body>
</html>