<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/listas.css">
</head>

<%
    Usuario userProfile = (Usuario) request.getAttribute("usuario");
%>

<body>
<%@ include file="barra_navegacion.jsp" %>
<div class=crearLista>
    <h2>¡Crea tus propias listas!</h2>
    <p>Organiza tus películas favoritas como tú quieras.</p>
    <form action="/crearLista" method="get">
        <button type="submit">Crear nueva lista</button>
    </form>
</div>
</body>
</html>
