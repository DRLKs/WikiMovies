<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.app.web.entity.Lista" %>
<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/crearLista.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>

<%
    Lista lista = (Lista) request.getAttribute("lista");
%>
<body>
<%@ include file="barra_navegacion.jsp" %>

<!-- Formulario con clase específica -->
<form:form action="/guardarCambiosLista" method="post" modelAttribute="editarLista" class="formulario-crear-lista">
    <form:hidden path="listaId"></form:hidden>
    <table class="tabla-formulario">
        <tr>
            <img src="<%=lista.getImgURL()%>" alt="Foto de <%=lista.getNombre()%>">
        </tr>
        <tr>
            <td class="label">Imagen (URL):</td>
            <td>
                <form:input path="fotoUrl" class="input-foto-url"/>
            </td>
        </tr>
        <tr>
            <td class="label">Nombre:</td>
            <td>
                <form:input path="nombre" class="input-nombre"></form:input>
            </td>
        </tr>
        <tr>
            <td class="label">Descripción:</td>
            <td>
                <form:textarea path="descripcion" class="textarea-descripcion"></form:textarea>
            </td>
        </tr>
        <tr>
            <td><form:button class="boton-guardar">Guardar</form:button></td>
        </tr>
    </table>
</form:form>
</body>
</html>
