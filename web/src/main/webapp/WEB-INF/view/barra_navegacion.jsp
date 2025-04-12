<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.entity.Usuario" %>

<html>
    <link rel="stylesheet" href="../../css/barra_navegacion.css">

<%
    Usuario usuario = null;
    if (session != null ) {
        usuario = (Usuario) session.getAttribute("usuario");
    }
%>

<body>

<div class="barra-navegacion">
    <h1> WikiMovies </h1>
    <div class="botones-container">
        <button>Peliculas</button>
        <button>Listas</button>
        <button>Miembros</button>
    </div>

    <div class="profile">
        <div class="profile">
            <%= usuario != null ? "Bienvenido " + usuario.getNombreUsuario() : "<a href='/login'>Iniciar sesión</a>" %>
            <%= usuario != null ? "<a href='" + request.getContextPath() + "/logout'>Cerrar sesión</a>" : "" %>
        </div>
    </div>
</div>

</body>