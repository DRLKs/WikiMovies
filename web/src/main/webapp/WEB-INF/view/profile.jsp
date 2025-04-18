<%@ page import="com.app.web.entity.Lista" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/profile.css">

<body>

<%@ include file="barra_navegacion.jsp" %>

<%
    Usuario userProfile = (Usuario) request.getAttribute("usuario");
    Set<Lista> listas = userProfile.getListas();
%>

<div class="profile-container">
    <div class="profile-header">
        <div class="profile-avatar">
            <img src="<%= null != null ? "userMiembro.avatarUrl" : "../../img/default-avatar.png"%>" alt="Avatar de <%=userProfile.getNombreUsuario()%>">
        </div>
        <div class="profile-info">
            <h1 class="profile-username"><%=userProfile.getNombreUsuario()%></h1>
            <p class="profile-bio"><%=null != null ? "usuario.bio" : "Sin biografía"%></p>
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
            <span class="stat-count"><%= 0 %></span>
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
</div>

<script>
    document.querySelectorAll('.follow-btn').forEach(button => {
        button.addEventListener('click', function() {
            const userId = this.getAttribute('data-userid');
            fetch('/usuario/seguir', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `usuarioId=${userId}`
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    this.textContent = this.textContent === 'Seguir' ? 'Dejar de seguir' : 'Seguir';
                    // Actualizar contador de seguidores
                    const seguidoresElement = document.querySelector('.stat-count');
                    const seguidores = parseInt(seguidoresElement.textContent);
                    seguidoresElement.textContent = this.textContent === 'Dejar de seguir' ? 
                        (seguidores + 1) : (seguidores - 1);
                }
            });
        });
    });
</script>

</body>
</html>