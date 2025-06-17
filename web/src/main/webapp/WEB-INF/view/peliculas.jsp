<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/index.css">
    <link rel="stylesheet" href="../../css/peliculas.css">
    <link rel="stylesheet" href="../../css/botonesPelicula.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
          crossorigin="anonymous"/>
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    String titulo = (String) request.getAttribute("titulo");
    List<PeliculaDTO> peliculas = (List<PeliculaDTO>) request.getAttribute("peliculas");
    boolean peliculaFavorita = false;
    boolean peliculaVista = false;
    ListaDTO favoritas = (ListaDTO) request.getAttribute("favoritas");
    ListaDTO vistas = (ListaDTO) request.getAttribute("vistas");

%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<%
    boolean usuarioAutenticado = usuario != null;
    if(usuario != null && usuario.getRol() == 2) {
%>
<a href="/crearPelicula" class="crear-pelicula-btn">CREAR PELICULA</a>
<%
    }
%>

<div class="info-busqueda-container">
    <% if(titulo != null && !titulo.trim().equals("")) {%>
    <h2> Mostrando <%= peliculas.size() %> resultados para "<%=titulo%>"</h2>
    <%
        }
    %>
</div>

<div class="peliculas-container">

    <%
        if (peliculas.isEmpty()) {
    %>
    <div class="no-movies-message">
        <h3>No se encontraron películas</h3>
        <p>Intenta con diferentes criterios de búsqueda.</p>
    </div>
    <%
    } else {
        for (PeliculaDTO pelicula : peliculas) {
            if (usuarioAutenticado) {
                if (vistas != null) {
                    peliculaVista = vistas.getPeliculas().contains(pelicula);
                }
                if (favoritas != null) {
                    peliculaFavorita = favoritas.getPeliculas().contains(pelicula);
                }
            } else {
                peliculaVista = false;
                peliculaFavorita = false;
            }
    %>

    
    <div class="pelicula-container" onclick="location.href='film?id=<%= pelicula.getId() %>'">

        <!-- SE MUESTRA EL POSTER DE LA PELICULA -->
        <div class="pancarta-container">
            <img src="<%= pelicula.getPoster() %>" alt="Pancarta Pelicula">
        </div>
        <!---------------------------------------------------------->

        <!-- SE MUESTRA LA INFO BASICA DE LA PELI -->
        <div class="informacion-container">
            <div class="informacion-basica-container">
                <h3><%= pelicula.getTitulo() + " (" + pelicula.getFechaEstreno().getYear() + ")"%></h3>
                Nota por la crítica: <%= pelicula.getMediaVotos()%>
            </div>
            <div class="descripcion-container">
                <%= pelicula.getDescripcion()%>
            </div>
        </div>
        <!---------------------------------------------------------->
        <!-- BOTONES DE ELIMINAR/EDITAR PELICULA -->
        <%
            if (usuarioAutenticado && usuario.getRol() == 2) {
        %>
        <form action="/eliminarPelicula" method="post" class="delete-form" onsubmit="event.stopPropagation(); return confirm('¿Seguro que quieres eliminar esta película?');">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button delete-btn" title="Eliminar película" onclick="event.stopPropagation();">
                <i class="fa fa-trash"></i>
            </button>
        </form>

        <form action="/editarPelicula" method="post" class="edit-form">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button" title="Editar película" onclick="event.stopPropagation();">
                <i class="fa fa-pencil"></i>
            </button>
        </form>
        <%
            }
        %>

        <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "VISTAS" -->
        <!-- Si el usuario está autenticado, funciona normalmente; si no, redirige a login -->
        <% if (usuarioAutenticado) { %>
        <form action="/seen" method="post" class="seen-form">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button" title="Marcar como vista" onclick="event.stopPropagation();">
                <i class="<%= peliculaVista ? "fas fa-eye" : "fas fa-eye-slash" %>"></i>
            </button>
        </form>
        <% } else { %>
        <a href="/login" class="seen-form favorite-button" title="Inicia sesión para marcar como vista">
            <i class="fas fa-eye-slash"></i>
        </a>
        <% } %>

        <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "FAVORITAS" -->
        <!-- Si el usuario está autenticado, funciona normalmente; si no, redirige a login -->
        <% if (usuarioAutenticado) { %>
        <form action="/favorite" method="post" class="favorite-form">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button" title="Añadir a favoritas" onclick="event.stopPropagation();">
                <i class="heart-icon <%= peliculaFavorita ? "active" : "" %>">❤</i>
            </button>
        </form>
        <% } else { %>
        <a href="/login" class="favorite-form favorite-button" title="Inicia sesión para añadir a favoritas">
            <i class="heart-icon">❤</i>
        </a>
        <% } %>
        <!---------------------------------------------------------->

    </div>
    
    <%
            }
        }
    %>
</div>

<script src="../../js/login.js"></script>
<script src="../../js/addToList.js"></script>

</body>
</html>