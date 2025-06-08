<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies - Crear Lista </title>
    <link rel="stylesheet" href="../../css/crearLista.css">
    <link rel="stylesheet" href="../../css/index.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>
<body>
<%@ include file="barra_navegacion.jsp" %>

<div class="container-crear-lista">
    <div class="formulario-wrapper">
        <h2 class="titulo-crear-lista">Crear Nueva Lista</h2>
        <p class="subtitulo-crear-lista">Crea una lista personalizada. Podrás añadir películas después de crearla.</p>
        
        <!-- Formulario para crear lista -->
        <form:form action="/guardarLista" method="post" modelAttribute="nuevaLista" class="formulario-crear-lista">
            
            <div class="campo-formulario">
                <label for="nombre" class="label-campo">Nombre de la Lista *</label>
                <form:input 
                    path="nombre" 
                    id="nombre"
                    class="input-campo" 
                    placeholder="Ej: Mis películas favoritas de acción"
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
                    placeholder="https://ejemplo.com/imagen.jpg (opcional)"
                    type="url"/>
                <small class="ayuda-texto">Si no proporcionas una imagen, se usará una predeterminada</small>
            </div>

            <div class="vista-previa-imagen" id="vistaPrevia" style="display: none;">
                <label class="label-campo">Vista previa:</label>
                <img id="imagenPrevia" src="" alt="Vista previa" class="imagen-previa"/>
            </div>

            <div class="botones-formulario">
                <a href="/listas" class="boton-cancelar">Cancelar</a>
                <form:button type="submit" class="boton-crear">Crear Lista</form:button>
            </div>
            
        </form:form>
    </div>
</div>

<script>
// Script para vista previa de imagen
document.getElementById('fotoUrl').addEventListener('input', function() {
    const url = this.value;
    const vistaPrevia = document.getElementById('vistaPrevia');
    const imagenPrevia = document.getElementById('imagenPrevia');
    
    if (url && url.startsWith('http')) {
        imagenPrevia.src = url;
        imagenPrevia.onload = function() {
            vistaPrevia.style.display = 'block';
        };
        imagenPrevia.onerror = function() {
            vistaPrevia.style.display = 'none';
        };
    } else {
        vistaPrevia.style.display = 'none';
    }
});

// Validación del formulario
document.querySelector('.formulario-crear-lista').addEventListener('submit', function(e) {
    const nombre = document.getElementById('nombre').value.trim();
    if (!nombre) {
        e.preventDefault();
        alert('Por favor, introduce un nombre para la lista');
        document.getElementById('nombre').focus();
    }
});
</script>

</body>
</html>
