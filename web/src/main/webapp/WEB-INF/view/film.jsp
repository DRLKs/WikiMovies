<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/film.css">

    <%
        boolean peliculaFavorita = (boolean) request.getAttribute("peliculaFavorita");
        Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
    %>

<body>

<%@ include file="barra_navegacion.jsp" %>

    <div class="pelicula-content">
        <div class="poster-container">
            <img src="<%= pelicula.getPoster() %>" alt="No tenemos pancarta para esta película">
        </div>
        <div class="informacion-container">
            <h3> <%= pelicula.getTitulo() + "   (" + pelicula.getFechaEstreno().getYear() + ")" %> </h3>
            <i> <%= pelicula.getEslogan() %> </i>
            <div class="descripcion-container">
                <%= pelicula.getDescripcion() %>
            </div>
            <%= "⏳" + pelicula.getDuracion() + " mins" %>

            <%
                if (pelicula.getGeneros() != null && !pelicula.getGeneros().isEmpty()) {
            %>
                <div class="generosTags-container">
                    <%
                        for (com.app.web.entity.Genero genero : pelicula.getGeneros()) {
                    %>
                        <span class="genero-tag"><%= genero.getNombre() %></span>
                    <%
                        }
                    %>
                </div>
            <%
                }
            %>

            <div class="favorite-container">
                <form action="/favorite" method="post">
                    <input type="hidden" name="id" value="<%= pelicula.getId() %>">
                    <button type="submit" class="favorite-button">
                        <i class="heart-icon<%= peliculaFavorita ? "-favorite" : "" %>">❤</i>
                    </button>
                </form>
            </div>
        </div>
    </div>


</body>
</html>