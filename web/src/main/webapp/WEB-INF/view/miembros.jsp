<%@ page import="java.util.List" %>
<%@ page import="com.app.web.dto.UsuarioDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/miembros.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    List<UsuarioDTO> usuarios = (List<UsuarioDTO>) request.getAttribute("miembros");
    String filtro = (String) request.getAttribute("filtro");
%>

<body>

    <%@ include file="barra_navegacion.jsp" %>    

    <h1 class="page-title">Miembros de WikiMovies</h1>

    <div id="filtroMiembros">
        <form action="/miembros/filtro" method="GET">
            <input
                    type="text"
                    name="nombre"
                    id="nombreUsuario"
                    placeholder="Buscar miembros..."
                    autocomplete="off"
                    maxlength="30"
                    <% if(filtro!=null){%>
                        value="<%=filtro%>"
                    <%}%>
            />
            <button type="submit">Buscar</button>
        </form>
    </div>

    <div class="miembros-container">
        <div class="miembros-grid">

            <%
                if(usuarios != null && !usuarios.isEmpty()) {
                    for(UsuarioDTO userMiembro : usuarios) {
            %>
                    <div class="miembro-card" onclick="location.href='/profile?id=<%= userMiembro.getIdUsuario() %>'">
                        <div>
                            <img class="miembro-profile-image" src="<%= userMiembro.getAvatar() != null ? userMiembro.getAvatar() : "../../img/default-avatar.png"%>" alt="Avatar de <%=userMiembro.getNombreUsuario()%>">
                            <div class="miembro-nombre"><%= userMiembro.getNombreUsuario() %></div>
                            <div class="miembro-info"><% if(!userMiembro.getTiempoRegistrado().equals("Usuario nuevo")) {%>Se unió hace <%= userMiembro.getTiempoRegistrado() %> <% } else { %><%= userMiembro.getTiempoRegistrado() %> <% } %></div>
                        </div>
                    <%
                        if(usuario != null && userMiembro.getIdUsuario() != usuario.getIdUsuario()){
                            if(usuario.sigueA(userMiembro)) { %>
                                <a class="unfollow-btn" href="/dejarSeguir?id=<%= userMiembro.getIdUsuario() %>" >Dejar de seguir</a>
                            <% } else { %>
                                <a class="follow-btn" href="/seguir?id=<%= userMiembro.getIdUsuario() %>" >Seguir</a>
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