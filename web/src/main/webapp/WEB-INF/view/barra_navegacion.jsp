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
    <a href="/"><h1> WikiMovies </h1> </a>
    <div class="botones-container">
        <a href="<%= request.getContextPath() %>/peliculas" class="nav-button">Peliculas</a>
        <a href="<%= request.getContextPath() %>/listas" class="nav-button">Listas</a>
        <a href="<%= request.getContextPath() %>/miembros" class="nav-button">Miembros</a>
    </div>

    <div class="search-container">
        <form action="/search" method="GET" class="search-form">
            <div class="form-group">
                <input
                        type="text"
                        id="tituloPelicula"
                        name="title"
                        class="form-input"
                        placeholder="Buscar peliculas..."
                        autocomplete="off"
                >
            </div>

            <!-- Si quieres añadir filtros adicionales expandibles -->
            <div class="filters" style="display: none;">
                <!-- Aquí puedes añadir más filtros como géneros, año, etc. -->
            </div>

            <button type="button" class="filters-toggle">
                Mostrar filtros avanzados
            </button>
        </form>
    </div>

    <div class="profile">
        <div class="profile">
            <% if (usuario != null) { %>
                <span>Bienvenido <%= usuario.getNombreUsuario() %></span>
                <a href="profile?id=<%= usuario.getId() %>" >
                    <img class="profile-image" src="<%= (usuario.getAvatarUrl() != null) ? usuario.getAvatarUrl() : "../../img/default-avatar.png" %>" alt="Avatar de <%= usuario.getNombreUsuario() %>">
                </a>
                <a href="<%= request.getContextPath() %>/logout">Cerrar sesión</a>
            <% } else { %>
                <a href="/login">Iniciar sesión</a>
            <% } %>
        </div>
    </div>
</div>

</body>