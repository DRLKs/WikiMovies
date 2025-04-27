<%@ page import="com.app.web.entity.Lista" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/profile.css">

<body>

<%@ include file="barra_navegacion.jsp" %>

<%
    Usuario userProfile = (Usuario) request.getAttribute("usuario");
    Set<Lista> listas = userProfile.getListas();
    int generoUsuario = userProfile.getGenero() != null ? userProfile.getGenero() : 0;
    LocalDate fechaNacimientoUsuario = userProfile.getNacimientoFecha() != null ? LocalDate.parse(LocalDate.ofInstant(userProfile.getNacimientoFecha(), java.time.ZoneOffset.UTC).toString()) : null;
%>

<div class="profile-container">
    <div class="profile-header">
        <div class="profile-avatar">
            <img src="<%= userProfile.getAvatarUrl() != null ? userProfile.getAvatarUrl() : "../../img/default-avatar.png"%>" alt="Avatar de <%=userProfile.getNombreUsuario()%>">
        </div>
        <div class="profile-info">
            <h1 class="profile-username"><%=userProfile.getNombreUsuario()%></h1>
            <p class="profile-bio"><%=userProfile.getGenero() != null && !userProfile.getBiografia().isEmpty() ? userProfile.getBiografia() : "Sin biografía"%></p>
            <div class="profile-actions">
                <% if (session.getAttribute("usuarioActual") != null && !session.getAttribute("usuarioActual").equals(request.getAttribute("usuario"))) { %>
                    <button class="follow-btn" data-userid="<%=userProfile.getId()%>">
                        <%=false ? "Dejar de seguir" : "Seguir"%>
                    </button>
                <% } %>
            </div>
        </div>
    </div>
    
    <div class="profile-stats">
        <div class="stat-box">
            <span class="stat-count"><%= userProfile.getSeguidores().size() %></span>
            <span class="stat-label">Seguidores</span>
        </div>
        <div class="stat-box">
            <span class="stat-count"><%= 0 %></span>
            <span class="stat-label">Siguiendo</span>
        </div>
        <div class="stat-box">
            <span class="stat-count"><%= userProfile.getListas().size() %></span>
            <span class="stat-label">Listas</span>
        </div>
    </div>
    
    <div class="profile-lists">
        <h2>Listas de <%= userProfile.getNombreUsuario() %> </h2>
        <div class="lists-container">
            <% if ( listas  != null  && !listas.isEmpty() ) { %>
                <% for (Lista lista : listas ) { %>
                    <div class="list-card">
                        <div class="list-thumbnail">
                            <img src="<%=null != null ? "lista.imagenUrl" : "../../img/default-list.png"%>" alt="<%= lista.getNombre() %>">
                        </div>
                        <div class="list-info">
                            <h3 class="list-title"><%=lista.getNombre()%></h3>
                            <p class="list-description"><%=lista.getNombre() %></p>
                            <span class="list-count"><%= lista.getPeliculas().size() %> películas</span>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <p class="no-lists">Este usuario no tiene listas todavía.</p>
            <% } %>
        </div>
    </div>

    <%
        if ( usuario != null && usuario.getId() == userProfile.getId() ){
    %>
    <div class="profile-editar">
        <button id="editProfileBtn" class="edit-profile-btn">Editar perfil</button>
        
        <div id="editProfileModal" class="modal">
            <div class="modal-content">
                <span class="close-modal">&times;</span>
                <h2>Editar tu perfil</h2>
                <form id="editProfileForm" action="/profile/update" method="POST" enctype="multipart/form-data">

                    <div class="form-group">
                        <label for="avatar">Foto de perfil:</label>
                        <div class="avatar-preview">
                            <img id="avatarPreview" placeholder="URL imagen" src="<%= userProfile.getAvatarUrl() != null ? userProfile.getAvatarUrl() : "../../img/default-avatar.png"%>" alt="Avatar">
                        </div>
                        <input type="text" id="avatar" name="avatar">
                    </div>
                    
                    <div class="form-group">
                        <label for="nombreUsuario">Nombre de usuario:</label>
                        <input type="text" id="nombreUsuario" name="nombreUsuario" value="<%= userProfile.getNombreUsuario() %>">
                    </div>

                    <div class="form-group">
                        <label for="fechaNacimiento">Fecha Nacimiento:</label>
                        <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                               value="<%= fechaNacimientoUsuario != null ? fechaNacimientoUsuario : "" %>"
                        <% if (userProfile.getNacimientoFecha() == null) { %>
                            <span class="fecha-placeholder">No indicada</span>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="generos">Género:</label>

                        <label class="radio-label">
                            <input type="radio" name="genero" value="1" <%= generoUsuario == 1 ? "checked" : "" %>> Hombre
                        </label>

                        <label class="radio-label">
                            <input type="radio" name="genero" value="2" <%= generoUsuario == 2 ? "checked" : "" %>> Mujer
                        </label>

                        <label class="radio-label">
                            <input type="radio" name="genero" value="3" <%= generoUsuario == 3 ? "checked" : "" %>> Otro
                        </label>
                    </div>

                    <div class="form-group">
                        <label for="biografia">Biografía:</label>
                        <textarea id="biografia" name="biografia" maxlength="500" rows="7"><%= userProfile.getBiografia() != null ? userProfile.getBiografia() : "" %></textarea>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="cancel-btn">Cancelar</button>
                        <button type="submit" class="save-btn">Guardar cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%
        }
    %>

</div>

<script type="text/javascript" src="../../js/updateProfile.js"></script>

</body>
</html>