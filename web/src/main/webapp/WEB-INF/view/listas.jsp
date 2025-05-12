<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/listas.css">
</head>



<body>
<%@ include file="barra_navegacion.jsp" %>

    <div class="navegar-listas">
        <%if(usuario != null){%>
        <a href="/listasPopulares">Listas Populares</a>
        <a href="/listas/listasSeguidos?id=<%=usuario.getId()%>">Listas Seguidos</a>
        <a href="/listas/misListas?id=<%=usuario.getId()%>">Mis listas</a>
        <%}else{%>
        <a href="/listasPopulares">Listas Populares</a>
        <%}%>
    </div>

    <div class="crearLista">
        <h2>¡Crea tus propias listas!</h2>
        <p>Organiza tus películas favoritas como tú quieras.</p>
        <form action="/crearLista" method="get">
            <button type="submit">Crear nueva lista</button>
        </form>
    </div>
</body>
</html>
