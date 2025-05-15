   <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/signupLogin.css">

<body>

<%
    String msgError = (String) request.getAttribute("mensaje");
    if (msgError == null ){
        msgError = "";
    }
%>

<header class="wiki-header">
    <div class="logo-container">
        <a href="${pageContext.request.contextPath}/" class="logo-link">
            <img src="../../img/logo_blanco.png" class="logo-icon" alt="LogoWikiMovies">
            <h1 class="logo-text">WikiMovies</h1>
        </a>
    </div>
</header>

<div class="container">
    
    <h1>Crear Cuenta</h1>
    <div class="error" id="error"><%= msgError %></div>
    <form:form action="${pageContext.request.contextPath}/crearCuenta" method="post" id="signup-form" modelAttribute="usuarioSignup">
        <div class="form-group">
            <label for="username">Nombre de usuario</label>
            <form:input type="text" id="username" path="username" placeholder="Ingresa tu nombre de usuario" required="required"/>
            <div class="error" id="username-error">El nombre de usuario debe tener al menos 4 caracteres</div>
        </div>

        <div class="form-group">
            <label for="email">Correo electrónico</label>
            <form:input type="email" id="email" path="email" placeholder="Ingresa tu correo electrónico" required="required"/>
            <div class="error" id="email-error">Por favor, ingresa un correo electrónico válido</div>
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <form:input type="password" id="password" path="password" placeholder="Ingresa tu contraseña" required="required"/>
            <div class="error" id="password-error">La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula y un número</div>
        </div>

        <div class="form-group">
            <label for="confirm-password">Confirmar contraseña</label>
            <form:input type="password" id="confirm-password" path="passwordConfirmed" placeholder="Confirma tu contraseña" required="required"/>
            <div class="error" id="confirm-password-error">Las contraseñas no coinciden</div>
        </div>

        <form:button type="submit" class="submit-btn">Registrarse</form:button>
        <div class="success-message" id="success-message">¡Cuenta creada con éxito!</div>
    </form:form>

    <div class="footer">
        ¿Ya tienes una cuenta?
        <a href="login" >Iniciar sesión</a>
    </div>
</div>

<script src="../../js/login.js"></script>

</body>
</html>