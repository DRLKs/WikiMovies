<%@ page import="com.app.web.entity.Lista" %>
<%@ page import="com.app.web.entity.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/listas.css">
</head>

<%
    Set<Lista> listas = (Set<Lista>) request.getAttribute("misListas");
%>

<body>
<%@ include file="barra_navegacion.jsp" %>

<div class="navegar-listas">
    <% if (usuario != null) { %>
    <a href="/listasPopulares">Listas Populares</a>
    <a href="/listasSeguidos?id=<%=usuario.getId()%>">Listas Seguidos</a>
    <a href="/misListas?id=<%=usuario.getId()%>" class="lista-activa">Mis listas</a>
    <% } else { %>
    <a href="listas/listasPopulares">Listas Populares</a>
    <% } %>
</div>

    <div class="todasListas">
        <h2>Mis listas</h2>
        <% for (Lista lista : listas) { %>
        <a href="/mostrarLista?listaId=<%= lista.getId() %>" class="lista-link">
            <div class="list-card">
                <div class="list-thumbnail">
                    <img src="<%= lista.getImgURL() != null ? lista.getImgURL() : "../../img/default-list.png" %>"
                         alt="<%= lista.getNombre() %>">

                </div>
                <div class="list-info">
                    <h3 class="list-title"><%= lista.getNombre() %></h3>
                    <p class="list-description"><%= lista.getDescripcion() %></p>
                    <p class="list-autor"><%=lista.getIdUsuario().getNombreUsuario()%></p>
                    <span class="list-count"><%= lista.getPeliculas().size() %> películas</span>
                </div>
            </div>
        </a>
        <% } %>
    </div>
    <div class="crearLista">
        <h2>¡Crea tus propias listas!</h2>
        <p>Organiza tus películas favoritas como tú quieras.</p>
        <form action="/crearLista" method="get">
            <button type="submit">Crear nueva lista</button>
        </form>
    </div>
</body>
</html>
