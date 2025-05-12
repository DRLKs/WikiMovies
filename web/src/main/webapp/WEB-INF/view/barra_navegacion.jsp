<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <div class="search-container" style="display: flex; align-items: flex-start; gap: 16px;">
        <form:form action="/search" method="GET" modelAttribute="filtroBusquedaDTO" style="display: flex; align-items: center; gap: 8px;">
            <!-- Campo de búsqueda -->
            <form:input
                    type="text"
                    id="tituloPelicula"
                    path="titulo"
                    placeholder="Buscar películas..."
                    autocomplete="off"
                    maxlength="30"
                    style="padding: 8px 12px; border-radius: 20px; border: none; background-color: #333; color: white; width: 200px;"
            />

            <!-- Botón de filtros (a la derecha del input) -->
            <button type="button" class="filters-toggle" title="Mostrar filtros avanzados"
                    style="background: none; border: none; cursor: pointer; display: flex; align-items: center;">
                <img src="https://icones.pro/wp-content/uploads/2021/05/icone-de-filtre-rouge.png"
                     alt="Filtros"
                     style="width: 18px; height: 18px;" />
            </button>

            <!-- Filtros avanzados (aún más a la derecha) -->
            <div class="filters" style="display: none; margin-left: 16px;">
                <div style="background-color: #222; padding: 10px; border-radius: 8px;">
                    <h4 style="margin-top: 0; color: white;">Géneros</h4>
                    <div class="generos-list" style="display: flex; flex-direction: column; gap: 4px;">
                        <form:checkboxes
                                items="${generos}"
                                itemLabel="nombre"
                                itemValue="id"
                                path="generos"
                                class="genero-item"
                        />
                    </div>
                    <button type="submit"
                            style="margin-top: 8px; padding: 6px 12px; border: none; border-radius: 4px;
                               background-color: red; color: white; cursor: pointer;">
                        Buscar
                    </button>
                </div>
            </div>
        </form:form>
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

<script src="../../js/barraNavegacion.js" > </script>


</body>
</html>