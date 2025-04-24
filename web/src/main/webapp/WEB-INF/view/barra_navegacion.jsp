<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.entity.Usuario" %>
<%@ page import="com.app.web.entity.Genero" %>
<%@ page import="java.util.List" %>

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
                <label for="tituloPelicula"></label><input
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
                <!-- Mostrar géneros desde la base de datos -->
                <div class="generos-container">
                    <h4>Géneros</h4>
                    <div class="generos-list">
                        <%
                            List<Genero> generos = null;
                            try {
                                generos = (List<Genero>) request.getAttribute("generos");
                            } catch (Exception e) {
                                // Handle the exception silently
                            }
                            
                            if(generos != null && !generos.isEmpty()) {
                                for(Genero genero : generos) {
                        %>
                            <div class="genero-item">
                                <input type="checkbox" 
                                       id="genero_<%= genero.getId() %>" 
                                       name="generos" 
                                       value="<%= genero.getId() %>">
                                <label for="genero_<%= genero.getId() %>"><%= genero.getNombre() %></label>
                            </div>
                        <%
                                }
                            } else {
                        %>
                            <p>No hay géneros disponibles</p>
                        <% } %>
                    </div>
                </div>
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

<!-- Script para mostrar/ocultar filtros -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const filtersToggle = document.querySelector('.filters-toggle');
        const filters = document.querySelector('.filters');
        
        filtersToggle.addEventListener('click', function() {
            const isHidden = filters.style.display === 'none';
            filters.style.display = isHidden ? 'block' : 'none';
            filtersToggle.textContent = isHidden ? 'Ocultar filtros avanzados' : 'Mostrar filtros avanzados';
        });
    });
</script>

</body>
</html>