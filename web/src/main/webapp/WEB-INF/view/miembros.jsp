<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/miembros.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("miembros");
    Usuario user = (Usuario)session.getAttribute("usuario");
%>

<body>

    <%@ include file="barra_navegacion.jsp" %>

    <div id="filtroMiembros">
        <form action="/miembros/filtro" method="GET" style="display: flex; align-items: center; gap: 8px;">
            <input
                    type="text"
                    name="nombre"
                    id="nombreUsuario"
                    placeholder="Buscar miembros..."
                    autocomplete="off"
                    maxlength="30"
                    style="padding: 8px 12px; border-radius: 20px; border: none; background-color: #333; color: white; width: 200px;"
            />
            <button type="submit" style="padding: 8px 12px; border-radius: 20px; border: none; background-color: #555; color: white;">Buscar</button>
        </form>
    </div>

    <div class="miembros-container">
        <h1 class="page-title">Miembros de WikiMovies</h1>

        <div class="miembros-grid">

            <%
                if(usuarios != null && !usuarios.isEmpty()) {
                    for(Usuario userMiembro : usuarios) {
            %>
                <div class="miembro-card">
                    <a href="profile?id=<%= userMiembro.getId() %>" class="miembro-link">
                        <div>
                            <img class="profile-image" src="<%= userMiembro.getAvatarUrl() != null ? userMiembro.getAvatarUrl() : "../../img/default-avatar.png"%>" alt="Avatar de <%=userMiembro.getNombreUsuario()%>">
                            <div class="miembro-nombre"><%= userMiembro.getNombreUsuario() %></div>
                            <div class="miembro-info"><% if(!userMiembro.getTiempoRegistrado().equals("Usuario nuevo")) {%>Se unió hace <%= userMiembro.getTiempoRegistrado() %> <% } else { %><%= userMiembro.getTiempoRegistrado() %> <% } %></div>
                        </div>
                    </a>
                    <%
                        if(user != null && userMiembro.getId() != user.getId()){
                            if(user.sigueA(userMiembro)) { %>
                                <a class="unfollow-btn" href="/dejarSeguir?id=<%= userMiembro.getId() %>">Dejar de seguir</a>
                            <% } else { %>
                                <a class="follow-btn" href="/seguir?id=<%= userMiembro.getId() %>">Seguir</a>
                            <% } %>
                    <%
                        }
                    %>
                </div>



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