<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/miembros.css">

<body>
    
    <%@ include file="barra_navegacion.jsp" %>
    
    <h1 class="page-title">Miembros de WikiMovies</h1>
    
    <div class="miembros-grid">
        <% 
        List<Usuario> usuarios = (List<Usuario>) request.getAttribute("miembros");
        if(usuarios != null || usuarios.size() > 0) {
            for(Usuario userMiembro : usuarios) {
        %>
                <div class="miembro-card">
                    <img src="<%= request.getContextPath() %>/img/default-profile.png" alt="Foto de perfil" class="profile-image">
                    <div class="miembro-nombre"><%= userMiembro.getNombreUsuario() %></div>
                    <div class="miembro-info">Miembro desde hace tiempo</div>
                </div>
        <%
            }
        } else {
        %>
            <p>No hay miembros para mostrar</p>
        <% } %>
    </div>
</body>
</html>