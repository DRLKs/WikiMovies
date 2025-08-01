<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.dto.ListaDTO" %>

<html>
<head>
    <title> WikiMovies - Editar Lista </title>
    <link rel="stylesheet" href="../../css/crearLista.css">
    <link rel="stylesheet" href="../../css/index.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    ListaDTO lista = (ListaDTO) request.getAttribute("lista");
%>

<body>
<%@ include file="barra_navegacion.jsp" %>


<div class="formulario-wrapper">
    <%if(lista.getId() == -1) {%>
        <h2 class="titulo-crear-lista">Crear Nueva Lista</h2>
        <p class="subtitulo-crear-lista">Crea una lista personalizada. Podrás añadir películas después de crearla.</p>
    <%} else {%>
        <h2 class="titulo-crear-lista">Editar Lista</h2>
        <p class="subtitulo-crear-lista">Modifica los detalles de tu lista personalizada.</p>

        <!-- Vista previa de la imagen actual -->
        <div class="vista-previa-imagen">
            <img src="<%=lista.getFotoUrl()%>" alt="Foto de <%=lista.getNombre()%>" class="imagen-previa">
        </div>
    <%}%>

    <!-- Formulario para crear/editar lista -->
    <form:form action="/guardarLista" method="post" modelAttribute="lista" class="formulario-crear-lista">

        <form:hidden path="id"/>

        <div class="campo-formulario">
            <label for="nombre" class="label-campo">Nombre de la Lista *</label>
            <form:input
                path="nombre"
                id="nombre"
                class="input-campo"
                placeholder="Nombre de tu lista"
                required="true"
                maxlength="100"/>
        </div>

        <div class="campo-formulario">
            <label for="descripcion" class="label-campo">Descripción</label>
            <form:textarea
                path="descripcion"
                id="descripcion"
                class="textarea-campo"
                placeholder="Describe tu lista... (opcional)"
                maxlength="500"
                rows="4"/>
        </div>

        <div class="campo-formulario">
            <label for="fotoUrl" class="label-campo">Imagen de la Lista (URL)</label>
            <form:input
                path="fotoUrl"
                id="fotoUrl"
                class="input-campo"
                placeholder="https://ejemplo.com/imagen.jpg"
                type="url"/>
            <%if(lista != null) {%>
                <small class="ayuda-texto">Cambia la URL para actualizar la imagen de la lista</small>
            <%} else {%>
                <small class="ayuda-texto">Si no proporcionas una imagen, se usará una predeterminada</small>
            <%}%>
        </div>

        <div class="vista-previa-imagen" id="vistaPrevia" style="display: none;">
            <label class="label-campo">Nueva vista previa:</label>
            <img id="imagenPrevia" src="" alt="Vista previa" class="imagen-previa"/>
        </div>

        <div class="botones-formulario">
            <a href="#" class="boton-cancelar" onclick="window.history.back(); return false;">Cancelar</a>
            <form:button type="submit" class="boton-guardar">Guardar</form:button>
        </div>

    </form:form>
</div>


<script src="../../js/editarListaVistaPreviaImagen.js"></script>


</body>
</html>
