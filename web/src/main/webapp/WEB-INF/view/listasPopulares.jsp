<%@ page import="com.app.web.entity.Lista" %>
<%@ page import="com.app.web.entity.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/listas.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    List<Lista> listas = (List<Lista>) request.getAttribute("listas");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="navegar-listas">
    <% if (usuario != null) { %>
    <a href="/listasPopulares" class="lista-activa">Listas Populares</a>
    <a href="/listasSeguidos?id=<%=usuario.getId()%>">Listas Seguidos</a>
    <a href="/misListas?id=<%=usuario.getId()%>">Mis listas</a>
    <% } else { %>
    <a href="/listasPopulares" class="lista-activa">Listas Populares</a>
    <% } %>
</div>

<div class="todasListas">
    <h2>Listas más populares</h2>
    <% for (Lista lista : listas) { %>
    <a href="/mostrarLista?listaId=<%= lista.getId() %>" class="lista-link">
        <div class="list-card">
            <div class="list-thumbnail">
                <img src="<%= lista.getImgURL() != null ? lista.getImgURL() : "../../img/default-list.png" %>"
                     alt="<%= lista.getNombre() %>">
            </div>
            <div class="list-info">
                <h3 class="list-title"><%= lista.getNombre() %>
                </h3>
                <p class="list-description"><%= lista.getDescripcion() %>
                </p>
                <span class="list-count"><%= lista.getPeliculas().size() %> películas</span>
            </div>
        </div>
    </a>
    <% } %>
</div>
</body>
</html>
