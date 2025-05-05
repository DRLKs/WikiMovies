<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> WikiMovies </title>
  <link rel="stylesheet" href="../../css/crearLista.css">
</head>
<body>
<%@ include file="barra_navegacion.jsp" %>

<!-- Formulario con clase específica -->
<form:form action="/guardarLista" method="post" modelAttribute="nuevaLista" class="formulario-crear-lista">
  <table class="tabla-formulario">
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
      <td class="label">Películas a añadir:</td>
      <td>
        <form:select class="select-peliculas" path="peliculasId" multiple="multiple" items="${peliculas}" itemValue="id" itemLabel="titulo">
        </form:select>
      </td>
    </tr>
    <tr>
      <td><form:button class="boton-guardar">Guardar</form:button></td>
    </tr>
  </table>
</form:form>
</body>
</html>
