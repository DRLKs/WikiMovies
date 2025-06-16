<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.dto.ListaDTO" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/mostrarLista.css">
    <link rel="stylesheet" href="../../css/botonesPelicula.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" crossorigin="anonymous"/>
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>
    <%
        ListaDTO listaDTO = (ListaDTO) request.getAttribute("listaDTO");
    %>

<body>

    <%@ include file="barra_navegacion.jsp" %>

    <div class="container-header">
        <div>
            <img src="<%= listaDTO.getFotoUrl() != null ? listaDTO.getFotoUrl() : "../../img/default-list.png" %>"
                 alt="<%= listaDTO.getNombre() %>">
        </div>
        <div class="info">
            <h1 class="lista-name"><%=listaDTO.getNombre()%></h1>
            <p class="lista-descripcion"><%= listaDTO.getDescripcion() %></p>
            <p class="movie-count" > Número de películas: <%=listaDTO.getPeliculas().size()%></p>
        </div>
        <%if( usuario != null && (usuario.getIdUsuario().equals(listaDTO.getUsuarioId()) ) && (!listaDTO.getNombre().equals("Vistas") && !listaDTO.getNombre().equals("Favoritas"))){%>

            <div class="action-buttons">
                <form method="post" action="/editarLista?listaId=<%=listaDTO.getId()%>">
                    <button class="favorite-button" title="Editar lista">
                        <i class="fa fa-pencil"></i>
                    </button>
                </form>
                <button type="button" class="favorite-button delete-btn" title="Eliminar lista"
                        onclick="showDeleteListModal(<%=listaDTO.getId()%>, '<%=listaDTO.getNombre()%>')">
                    <i class="fa fa-trash"></i>
                </button>
            </div>

        <%}%>

    </div>    
    
    <table>
        <tr>
            <td> Nombre Películas </td>
            <td> Duración </td>
            <%if(usuario != null && listaDTO.getUsuarioId().equals(usuario.getIdUsuario())){%>
            <td></td>
            <%}%>
        </tr>    <%
        for(PeliculaDTO pelicula : listaDTO.getPeliculas()){
    %>
        <tr>
            <td>
                <div class="clickable" onclick="window.location.href='film?id=<%= pelicula.getId() %>';">
                    <%= pelicula.getTitulo() %>
                </div>
            </td>
            <td>
                <div class="clickable" onclick="window.location.href='film?id=<%= pelicula.getId() %>';">
                    <%= pelicula.getDuracion() %>
                </div>
            </td>
            <%if(usuario != null && listaDTO.getUsuarioId().equals(usuario.getIdUsuario())){%>
                <td class="delete-cell-wrapper">
                    <button class="favorite-button delete-btn" title="Eliminar película de la lista"
                        onclick="showDeleteMovieModal(<%= pelicula.getId() %>, '<%= pelicula.getTitulo() %>', <%= listaDTO.getId() %>)">
                        <i class="fa fa-times"></i>
                    </button>
                </td>
            <%}%>
        </tr>

    <%
        }
    %>

    </table>

    <!-- Modal de confirmación para eliminar lista -->
    <div id="deleteListModal" class="delete-confirmation-modal">
        <div class="delete-modal-overlay"></div>
        <div class="delete-modal-content">
            <div class="delete-modal-header">
                <h3>Eliminar Lista</h3>
            </div>
            
            <div class="delete-modal-body">
                <p class="delete-main-text">¿Eliminar la lista <strong id="deleteListName"></strong>?</p>
                <p class="delete-warning-text">Esta acción no se puede deshacer y se perderán todas las películas de la lista.</p>
            </div>
            
            <div class="delete-modal-footer">
                <button onclick="hideDeleteListModal()" class="btn-cancel-delete">
                    Cancelar
                </button>

                <form action="/eliminarLista?listaId=<%=listaDTO.getId()%>" method="post">
                    <button type="submit" class="btn-confirm-delete">
                        Eliminar
                    </button>
                </form>

            </div>
        </div>
    </div>

    <!-- Modal de confirmación para eliminar película de lista -->
    <div id="deleteMovieModal" class="delete-confirmation-modal">
        <div class="delete-modal-overlay"></div>
        <div class="delete-modal-content">
            <div class="delete-modal-header">
                <h3>Eliminar Película</h3>
            </div>
            
            <div class="delete-modal-body">
                <p class="delete-main-text">¿Eliminar <strong id="deleteMovieName"></strong> de la lista?</p>
                <p class="delete-warning-text">La película se quitará de esta lista pero seguirá disponible en WikiMovies.</p>
            </div>
            
            <div class="delete-modal-footer">
                <button onclick="hideDeleteMovieModal()" class="btn-cancel-delete">
                    Cancelar
                </button>
                <button onclick="confirmMovieDeletion()" class="btn-confirm-delete">
                    Eliminar
                </button>
            </div>
        </div>
    </div>

    <script src="../../js/confirmacion.js"></script>


</body>
</html>