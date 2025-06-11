<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page import="com.app.web.dto.GeneroDTO" %>
<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/film.css">
    <link rel="stylesheet" href="../../css/botonesPelicula.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
          crossorigin="anonymous"/>
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    boolean peliculaFavorita = (boolean) request.getAttribute("peliculaFavorita");
    boolean peliculaVista = (boolean) request.getAttribute("peliculaVista");
    PeliculaDTO pelicula = (PeliculaDTO) request.getAttribute("pelicula");
    List<ListaDTO> listasUsuario = (List<ListaDTO>) request.getAttribute("listasUsuario");
    List<ListaDTO> listasPelicula = (List<ListaDTO>) request.getAttribute("listasPelicula");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<div class="pelicula-content">
    <div class="poster-container">
        <img src="<%= pelicula.getPoster() %>" alt="No tenemos imagen para esta película">
    </div>
    <div class="informacion-container">
        <h3><%= pelicula.getTitulo() + "   (" + pelicula.getFechaEstreno().getYear() + ")" %>
        </h3>
        <i class="eslogan"><%= pelicula.getEslogan() %>
        </i>
        <!---------------------------------------------------------->
        <!-- BOTON DE EDITAR PELICULA -->
        <% 
            if (usuario != null && usuario.getRol() == 2) { 
        %>
            <form action="/eliminarPelicula" method="post" class="delete-form filmview" onsubmit="return confirm('¿Seguro que quieres eliminar esta película?');">
                <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
                <button type="submit" class="favorite-button delete-btn" title="Eliminar película">
                    <i class="fa fa-trash"></i>
                </button>
            </form>

            <form action="/editarPelicula" method="post" class="edit-form filmview">
                <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
                <button type="submit" class="favorite-button" title="Editar película">
                    <i class="fa fa-pencil"></i>
                </button>
            </form>           
        
        <% 
            } 
        %>
        <!---------------------------------------------------------->
        <!-- BOTON DE AÑADIR PELICULA A UNA LISTA -->
        <%
            if (usuario != null) {
        %>
        <div class="addToList">
            <button id="addToListBtn" class="addToList-btn" title="Añadir a lista">+</button>

            <div id="addToListModal" class="modal">
                <div class="modal-content">
                    <span class="close-modal">&times;</span>
                    <h2>Añadir "<%= pelicula.getTitulo() %>" a:</h2>

                    <form method="post" action="/addToList">
                        <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">                        <%
                            for (ListaDTO lista : listasUsuario) {
                        %>
                            <label>
                                <input type="checkbox" name="listasSeleccionadas" value="<%=lista.getId()%>"
                                       <% if(listasPelicula.contains(lista)){%>checked="checked"<%}%>/>
                            </label>
                        <%=lista.getNombre()%><br>
                        <%
                            }
                        %>

                        <div class="form-actions">
                            <button type="button" class="cancel-btn">Cancelar</button>
                            <button type="submit" class="save-btn">Guardar cambios</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%
            }
        %>

        <!---------------------------------------------------------->
        <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "VISTAS" -->
        <form action="/seen" method="post" class="seen-form">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button" title="Marcar como vista">
                <i class="<%= peliculaVista ? "fas fa-eye" : "fas fa-eye-slash" %>"></i>
            </button>
        </form>

        <!---------------------------------------------------------->
        <!-- BOTON PARA AÑADIR PELICULA A LA LISTA DE "FAVORITAS" -->
        <form action="/favorite" method="post" class="favorite-form">
            <input type="hidden" name="idPelicula" value="<%= pelicula.getId() %>">
            <button type="submit" class="favorite-button" title="Añadir a favoritas">
                <i class="heart-icon <%= peliculaFavorita ? "active" : "" %>">❤</i>
            </button>
        </form>
        <!---------------------------------------------------------->

        <div class="descripcion-container">
            <%= pelicula.getDescripcion() %>
        </div>

        <a href="/informacionPelicula?id=<%=pelicula.getId()%>" class="boton-masInfo">Más información</a>

        <div class="duration">
            <%= "⏳" + pelicula.getDuracion() + " mins" %>
        </div>

        <%
            if (pelicula.getGeneros() != null && !pelicula.getGeneros().isEmpty()) {
        %>
        <div class="generosTags-container">
            <%
                for (GeneroDTO genero : pelicula.getGeneros()) {
            %>
            <span class="genero-tag"><%= genero.getNombre() %></span>
            <%
                }
            %>
        </div>
        <%
            }
        %>

    </div>
</div>

<script src="../../js/addToList.js"></script>

</body>
</html>