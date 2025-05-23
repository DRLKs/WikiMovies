<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.app.web.entity.Lista" %>
<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.web.entity.Pelicula" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>

<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/profile.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>
<body>

<%@ include file="barra_navegacion.jsp" %>

<%
    Usuario userProfile = (Usuario) request.getAttribute("usuario");
    Set<Pelicula> peliculasFavoritas =  userProfile.getPeliculasFavoritas();
    List<ListaDTO> listasDTO = (List<ListaDTO>) request.getAttribute("listasDTO");
    Usuario user = (Usuario)session.getAttribute("usuario");
    Integer seguidos = (Integer) request.getAttribute("seguidos");
%>

<div class="profile-container">
    <div class="profile-header">
        <div class="profile-avatar">
            <img src="<%= userProfile.getAvatarUrl() != null ? userProfile.getAvatarUrl() : "../../img/default-avatar.png"%>" alt="Avatar de <%=userProfile.getNombreUsuario()%>">
        </div>
        <div class="profile-info">
            <h1 class="profile-username"><%=userProfile.getNombreUsuario()%></h1>
            <p class="profile-bio"><%=userProfile.getGenero() != null && !userProfile.getBiografia().isEmpty() ? userProfile.getBiografia() : "Sin biografía"%></p>

            <%
                if(user != null && userProfile.getId() != user.getId()){
                    if(user.sigueA(userProfile)) { %>
                        <a class="unfollow-btn" href="/dejarSeguir?id=<%= userProfile.getId() %>">Dejar de seguir</a>
                    <% } else { %>
                        <a class="follow-btn" href="/seguir?id=<%= userProfile.getId() %>">Seguir</a>
                    <% } %>
            <%
                }
            %>

        </div>

    </div>
    
    <div class="profile-stats">
        <div class="stat-box">
            <span class="stat-count"><%= userProfile.getSeguidores().size() %></span>
            <span class="stat-label">Seguidores</span>
        </div>
        <div class="stat-box">
            <span class="stat-count"><%= seguidos %></span>
            <span class="stat-label">Siguiendo</span>
        </div>
        <div class="stat-box">
            <span class="stat-count"><%= listasDTO != null ? listasDTO.size() : 0 %></span>
            <span class="stat-label">Listas</span>
        </div>
    </div>
      <div class="profile-lists">
        <h2>Listas de <%= userProfile.getNombreUsuario() %> </h2>
        <div class="lists-container">
            <% if (listasDTO != null && !listasDTO.isEmpty()) { %>
                <% for (ListaDTO lista : listasDTO) { %>
                    <a href="/mostrarLista?listaId=<%= lista.getId() %>" class="lista-link">
                    <div class="list-card">
                        <div class="list-thumbnail">
                            <img src="<%= lista.getFotoUrl() != null ? lista.getFotoUrl() : "../../img/default-list.png" %>"
                                 alt="<%= lista.getNombre() %>">
                        </div>
                        <div class="list-info">
                            <h3 class="list-title"><%= lista.getNombre() %></h3>
                            <p class="list-description"><%= lista.getDescripcion() %></p>
                            <span class="list-count"><%= lista.getPeliculasId().size() %> películas</span>
                        </div>
                    </div>
                    </a>
                <% } %>
            <% } %>
            </div>
        </div>
    </div>

    <%
        if ( usuario != null && usuario.getId() == userProfile.getId()){
    %>
    <div class="profile-editar">
        <button id="editProfileBtn" class="edit-profile-btn">Editar perfil</button>

        <div id="editProfileModal" class="modal">
            <div class="modal-content">
                <span class="close-modal">&times;</span>
                <h2>Editar tu perfil</h2>
                <form:form id="editProfileForm" action="/profile/update" method="POST" enctype="multipart/form-data" modelAttribute="usuarioProfile" >

                    <div class="form-group">
                        <label for="avatar">Foto de perfil:</label>
                        <div class="avatar-preview">
                            <img id="avatarPreview" placeholder="URL imagen" src="<%= userProfile.getAvatarUrl() != null ? userProfile.getAvatarUrl() : "../../img/default-avatar.png"%>" alt="Avatar">
                        </div>

                        <form:input type="text" id="avatar" path="avatar" placeholder="url" />

                    </div>

                    <div class="form-group">
                        <label for="nombreUsuario">Nombre de usuario:</label>
                        <form:input type="text" id="nombreUsuario" path="nombreUsuario" value="<%= userProfile.getNombreUsuario() %>"/>
                    </div>

                    <div class="form-group">
                        <label for="fechaNacimiento">Fecha Nacimiento:</label>
                        <form:input type="date" id="fechaNacimiento" path="fechaNacimiento"/>
                    </div>

                    <div class="form-group">
                        <label for="generos">Género:</label>

                        <label class="radio-label">
                            <form:radiobutton path="genero" value="1"/> Hombre
                        </label>

                        <label class="radio-label">
                            <form:radiobutton path="genero" value="2"/> Mujer
                        </label>

                        <label class="radio-label">
                            <form:radiobutton path="genero" value="3" /> Otro
                        </label>
                    </div>

                    <div class="form-group">
                        <label for="biografia">Biografía:</label>
                        <textarea id="biografia" name="biografia" maxlength="500" rows="7"><%= userProfile.getBiografia() != null ? userProfile.getBiografia() : "" %></textarea>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="cancel-btn">Cancelar</button>
                        <form:button type="submit" class="save-btn">Guardar cambios</form:button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <%
        }
    %>


<script type="text/javascript" src="../../js/updateProfile.js"></script>

</body>
</html>