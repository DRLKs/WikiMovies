<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/search.css">

<%
    String titulo = (String) request.getAttribute("titulo");
    List<Pelicula> peliculas = (List<Pelicula>) request.getAttribute("peliculas");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

    <div class="info-busqueda-container" >
        <h2> Mostrando resultado para "<%=titulo%>"</h2> <br>
        ----------------------------------------
    </div>

    <div class="peliculas-container">

        <%
            if ( peliculas.size() < 1 ){
        %>
            <h1> No hay películas con este filtro </h1>
        <%
            }
        %>

        <%
            for ( Pelicula pelicula : peliculas ){
        %>

        <div class="pelicula-container">

            <div class="pancarta-container">
                <img src="" alt="Pancarta Pelicula">
            </div>
            <div class="informacion-container">

                <div class="informacion-basica-container">
                    <h3> <%= pelicula.getTitulo() + " (" + pelicula.getFechaEstreno().getYear() + ")"%> </h3>
                    Nota por la crítica: <%= pelicula.getMediaVotos()%>
                </div>
                <div class="descripcion-container">
                    <%= pelicula.getDescripcion()%>

                </div>
            </div>
        </div>
        <%  } %>
    </div>
</body>
</html>