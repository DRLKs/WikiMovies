<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.web.entity.Genero" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/index.css">

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
                    <a href="informacionPelicula?id=<%= p.getId() %>">
                        <img src="<%= p.getPoster() %>"
                             alt="Póster de <%= p.getTitulo() %>"
                             class="poster-ranking"
                             style="width: 180px; height: 240px; object-fit: cover; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.3); transition: transform 0.2s; margin: 5px;"
                        >
                    </a>
                    <!-- Este es el funcionamiento del boton, estaria bien hacerlo con js -->
                    <button type="button" class="button-toggle" title="Añadir pelicula a lista">
                        <img src="https://sdmntpritalynorth.oaiusercontent.com/files/00000000-cf14-6246-96eb-5ba93390080d/raw?se=2025-05-14T20%3A10%3A57Z&sp=r&sv=2024-08-04&sr=b&scid=00000000-0000-0000-0000-000000000000&skoid=b928fb90-500a-412f-a661-1ece57a7c318&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-05-14T08%3A01%3A11Z&ske=2025-05-15T08%3A01%3A11Z&sks=b&skv=2024-08-04&sig=MDhoEq3i5wCafmfx9DjOq6200G9AJiYAhOzVm%2Bvt0LU%3D"
                             alt="Añadir pelicula a lista"
                             class="AnadirLista"
                             style="width: 20px; height: 20px; padding: 2px; border: none; background-color: transparent; cursor: pointer;"
                        >
                    </button>

                    <%
                        }
                    %>



                </div>
            </div>
            <%for (Genero g: generos){%>
            <div class="peliculas-generos">
                <h3> Peliculas de <%=g.getNombre()%></h3>
                <div class="lista-peliculas">
                    <%for(Pelicula p : peliculaList){%>
                        <%if(p.getGeneros().contains(g)){%>
                            <a href="informacionPelicula?id=<%= p.getId() %>">
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