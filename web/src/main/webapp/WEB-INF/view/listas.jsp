<%@ page import="com.app.web.entity.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/listas.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    List<ListaDTO> listas = (List<ListaDTO>) request.getAttribute("listas");
    String apartado = (String) request.getAttribute("apartado");
%>

<body>
<%@ include file="barra_navegacion.jsp" %>

<div class="navegar-listas">
    <% if (usuario != null) { %>
    <a href="/listas" <% if(apartado.equals("populares")) { %>class="lista-activa"<% } %> >Listas Populares</a>
    <a href="/listasSeguidos?id=<%=usuario.getIdUsuario()%>" <% if(apartado.equals("seguidos")) { %>class="lista-activa"<% } %> >Listas Seguidos</a>
    <a href="/misListas?id=<%=usuario.getIdUsuario()%>" <% if(apartado.equals("misListas")) { %>class="lista-activa"<% } %> >Mis listas</a>
    <% } else { %>
    <a href="listas" class="lista-activa">Listas Populares</a>
    <% } %>
</div>

<% if(apartado.equals("misListas")) { %>
<div class="crearLista">
    <div class="crearLista-texto">
        <h2>¡Crea tus propias listas!</h2>
        <p>Organiza tus películas favoritas como tú quieras.</p>
    </div>
    <form action="/crearLista" method="get">
        <button type="submit">Crear nueva lista</button>
    </form>
</div>
<% } %>

<div class="todasListas">
    <% if(apartado.equals("populares")) { %>
        <h2>Listas más populares</h2>
    <% } else if(apartado.equals("seguidos")){ %>
        <h2>Listas de tus seguidos</h2>
    <% } else {%>
        <h2>Mis listas</h2>
    <% } %>

    <% for (ListaDTO lista : listas) { %>
    <a href="/mostrarLista?listaId=<%= lista.getId() %>" class="lista-link">
        <div class="list-card">
            <div class="list-thumbnail">
                <img src="<%= lista.getFotoUrl() != null ? lista.getFotoUrl() : "../../img/default-list.png" %>"
                     alt="<%= lista.getNombre() %>">

            </div>
            <div class="list-info">
                <h3 class="list-title"><%= lista.getNombre() %></h3>
                <p class="list-description"><%= lista.getDescripcion() %></p>
                <p class="list-propietario">Usuario: <%=lista.getNombreUsuario()%></p>

                <!-- El DTO no tiene usuario, así que no mostramos el nombre -->
                <span class="list-count"><%= lista.getPeliculasId().size() %> películas</span>
            </div>
        </div>
    </a>
    <% } %>
</div>

</body>
</html>
