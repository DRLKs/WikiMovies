<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title> WikiMovies </title>
<link rel="stylesheet" href="../../css/miembros.css">

<body>
    
    <%@ include file="barra_navegacion.jsp" %>
    

    <div class="miembros-container">
        <h1 class="page-title">Miembros de WikiMovies</h1>

        <div class="miembros-grid">

            <%
                List<Usuario> usuarios = (List<Usuario>) request.getAttribute("miembros");
                if(usuarios != null && !usuarios.isEmpty()) {
                    for(Usuario userMiembro : usuarios) {
            %>

            <a href="profile?id=<%= userMiembro.getId() %>" class="miembro-link">

                <div class="miembro-card">
                    <img class="profile-image" src="<%= userMiembro.getAvatarUrl() != null ? userMiembro.getAvatarUrl() : "../../img/default-avatar.png"%>" alt="Avatar de <%=userMiembro.getNombreUsuario()%>">
                    <div class="miembro-nombre"><%= userMiembro.getNombreUsuario() %></div>
                    <div class="miembro-info">Miembro desde hace tiempo</div>
                </div>

            </a>

            <%
                }
            } else {
            %>

            <p>No hay miembros para mostrar</p>

            <% } %>

        </div>
    </div>

</body>
</html>