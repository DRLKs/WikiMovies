<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: David
  Date: 31/03/2025
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> WikiMovies </title>
</head>

<%
    List<Pelicula> peliculaList = (List<Pelicula>) request.getAttribute("peliculas");
%>

<body>
    <h1>
        HOLAAAAAAAAAAa
    </h1>

<%
    for ( Pelicula pelicula : peliculaList ){

%>

<%= pelicula.getTitulo() %>
<br>
<%  } %>
</body>
</html>
