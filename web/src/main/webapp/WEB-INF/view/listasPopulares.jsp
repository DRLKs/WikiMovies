<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/listas.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    List<ListaDTO> listas = (List<ListaDTO>) request.getAttribute("listas");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="navegar-listas">
    <% if (usuario != null) { %>
    <a href="/listas" class="lista-activa">Listas Populares</a>
    <a href="/listasSeguidos?id=<%=usuario.getIdUsuario()%>">Listas Seguidos</a>
    <a href="/misListas?id=<%=usuario.getIdUsuario()%>">Mis listas</a>
    <% } else { %>
    <a href="/listas" class="lista-activa">Listas Populares</a>
    <% } %>
</div>

<div class="todasListas">
    <h2>Listas más populares</h2>
    <% for (ListaDTO lista : listas) { %>
    <a href="/mostrarLista?listaId=<%= lista.getId() %>" class="lista-link">
        <div class="list-card">
            <div class="list-thumbnail">
                <img src="<%= lista.getFotoUrl() != null ? lista.getFotoUrl() : "../../img/default-list.png" %>"
                     alt="<%= lista.getNombre() %>">
            </div>
            <div class="list-info">
                <h3 class="list-title"><%= lista.getNombre() %>
                </h3>
                <p class="list-description"><%= lista.getDescripcion() %>
                </p>
                <p class="list-propietario">Usuario: <%=lista.getNombreUsuario()%></p>

                <span class="list-count"><%= lista.getPeliculasId().size() %> películas</span>
            </div>
        </div>
    </a>
    <% } %>
</div>
</body>
</html>
