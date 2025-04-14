<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/search.css">

    <%
        Pelicula pelicula = (Pelicula) request.getAttribute("pelicula");
    %>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="peliculas-container">
    <%= pelicula.getTitulo() %>
</div>

</body>
</html>