<%@ page import="com.app.web.dto.PeliculaDTO" %>
<%@ page import="com.app.web.entity.Idioma" %>
<%@ page import="com.app.web.entity.Genero" %>
<%@ page import="com.app.web.dto.GeneroDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>WikiMovies</title>
    <link rel="stylesheet" href="../../css/editarPelicula.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    PeliculaDTO pelicula = (PeliculaDTO) request.getAttribute("pelicula");
    List<Idioma> idiomas = (List<Idioma>) request.getAttribute("idiomas");
    List<GeneroDTO> generos = (List<GeneroDTO>) request.getAttribute("generos");
%>

<body>

<%@ include file="barra_navegacion.jsp" %>

<form method="post" action="/guardarPelicula" class="saveMovieForm">
    <input type="hidden" name="idPelicula" value="<%= pelicula != null? pelicula.getId() : -1 %>">
    <table>
        <tr>
            <td>Título:</td>
            <td><input type="text" name="titulo" value="<%= pelicula != null? pelicula.getTitulo() : "" %>"/></td>
        </tr>
        <tr>
            <td>Fecha estreno:</td>
            <td><input type="date" name="fecha_estreno" value="<%= pelicula != null? pelicula.getFechaEstreno() : "" %>"/></td>
        </tr>
        <tr>
            <td>Presupuesto:</td>
            <td><input type="number" name="presupuesto" value="<%= pelicula != null? pelicula.getPresupuesto() : "" %>"/></td>
        </tr>
        <tr>
            <td>Ingresos:</td>
            <td><input type="number" name="ingresos" value="<%= pelicula != null? pelicula.getIngresos() : "" %>"/></td>
        </tr>
        <tr>
            <td>Duracion (min):</td>
            <td><input type="number" name="duracion" value="<%= pelicula != null? pelicula.getDuracion() : "" %>"/></td>
        </tr>
        <tr>
            <td>Descripción:</td>
            <td><textarea name="descripcion"><%=pelicula != null? pelicula.getDescripcion() : ""%></textarea></td>
        </tr>
        <tr>
            <td>Enlace:</td>
            <td><input type="text" name="enlace" size="70" value="<%= pelicula != null? pelicula.getEnlace() : "" %>"/></td>
        </tr>
        <tr>
            <td>Idioma original:</td>
            <td>
                <select name="idioma_original">
                    <% for(Idioma idioma: idiomas) {
                        String selected = "";
                        if (pelicula!=null && pelicula.getIdiomaOriginal() == idioma) {
                            selected = "selected";
                        }
                    %>
                    <option value="<%= idioma.getId() %>" label="<%= idioma.getNombre() %>" <%= selected %> />

                    <% } %>
                </select>
            </td>
        </tr>
        <tr>
            <td>Estado:</td>
            <td><input type="text" name="estatus" value="<%= pelicula != null? pelicula.getEstatus() : "" %>"/></td>
        </tr>
        <tr>
            <td>Eslogan:</td>
            <td><input type="text" name="eslogan" size="70" value="<%= pelicula != null? pelicula.getEslogan() : "" %>"/></td>
        </tr>
        <tr>
            <td>Poster:</td>
            <td><input type="text" name="poster" size="70" value="<%= pelicula != null? pelicula.getPoster() : "" %>"/></td>
        </tr>
        <tr>
            <td class="generos-cell">Géneros:</td>
            <td class="generos-cell">
                <div class="generos-checkbox-list" id="editar-generos-list">
                <% for(GeneroDTO genero: generos) {
                    var selected = "";
                    if (pelicula != null) {
                        for (Genero g : pelicula.getGeneros()) {
                            if (g.getId().equals(genero.getId())) {
                                selected = "checked";
                                break;
                            }
                        }
                    }
                %>
                    <label><input type="checkbox" name="generos" <%= selected %> value="<%= genero.getId() %>"><%= genero.getNombre()  %></label>
                <% } %>
                </div>
            </td>
        </tr>
    </table>
    <div class="form-actions">
        <a href="<%= pelicula != null? "/film?id="+pelicula.getId() : "/peliculas" %>" class="cancelar-btn">Cancelar</a>
        <button type="submit" class="save-btn">Guardar</button>
    </div>
</form>

</body>
</html>
