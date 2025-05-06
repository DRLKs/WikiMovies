<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
    <head>
        <title> WikiMovies </title>
        <link rel="stylesheet" href="../../css/index.css">
        <link rel="stylesheet" href="../../css/peliculas.css">
    </head>

    <%@ include file="barra_navegacion.jsp" %>
    <% List <Pelicula> peliculasTop = (List<Pelicula>) request.getAttribute("peliculas"); %>

    <body>
        <div style="text-align: center; margin-top: 20px;">
            <button onclick="history.back()" class="boton-volver">Volver</button>
        </div>
        <div class="container">

            <h3 class="ranking-titulo">ESTA ES LA SELECCIÓN DEL DÍA DE HOY:</h3>

            <div class="ranking-container">
                <%
                    int top = 1;
                    for (int i = 0; i < peliculasTop.size(); i++) {
                        Pelicula p = peliculasTop.get(i);
                %>
                <div class="pelicula-ranking">
                    <div class="ranking-etiqueta <%= top <= 3 ? "podio-" + top : "" %>">TOP <%= top %></div>
                    <h4><%= p.getTitulo() %></h4>
                    <a href="informacionPelicula?id=<%= p.getId() %>">
                        <img src="<%= p.getPoster() %>" alt="Póster de <%= p.getTitulo() %>" class="poster-ranking">
                    </a>
                    <div class="puntuacion">
                        ⭐ <%= String.format("%.1f", p.getPopularidad()) %>/10
                    </div>
                </div>

                <%
                        top++;
                    }
                %>

            </div>
        </div>
    </body>
</html>
