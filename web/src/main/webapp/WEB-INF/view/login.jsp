<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/signupLogin.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
          crossorigin="anonymous"/>
</head>

<body>

    <header class="wiki-header">
        <div class="logo-container">
            <a href="${pageContext.request.contextPath}/" class="logo-link">
                <img src="../../img/logo_blanco.png" class="logo-icon" alt="LogoWikiMovies">
                <h1 class="logo-text">WikiMovies</h1>
            </a>
        </div>
    </header>

<div class="container">
    <h1>Iniciar Sesión</h1>

    <div class="error-message" id="login-error" style="display:none;"></div>

    <form:form id="login-form" method="post" action="${pageContext.request.contextPath}/log" modelAttribute="usuarioLogin">
        <div class="form-group">
            <label for="email">Correo electrónico</label>
            <form:input type="email" id="email" path="email" placeholder="Ingresa tu correo electrónico" required="required"/>
            <div class="error" id="email-error">Por favor, ingresa un correo electrónico válido</div>
            <form:errors path="email" cssClass="error-message" />
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <form:password id="password" path="password" placeholder="Ingresa tu contraseña" required="required"/>
            <button type="button" onclick="togglePassword()" class="password-btn">
                <i class="fas fa-eye"></i>
            </button>
            <div class="error" id="password-error">Por favor, ingresa tu contraseña</div>
            <form:errors path="password" cssClass="error-message" />
        </div>

        <form:button type="submit" class="submit-btn">Iniciar Sesión</form:button>
        <div class="success-message" id="success-message">¡Sesión iniciada correctamente!</div>
    </form:form>


    <div class="footer">
        ¿No tienes una cuenta? <a href="/signup">Regístrate aquí</a>
    </div>

</div>

    <script src="../../js/login.js"></script>
    
    <%-- Script para mostrar mensaje de error si viene del servidor --%>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            <%
            String msg = (String) request.getAttribute("mensaje");
            if(msg != null) {
            %>
                // Obtener el elemento de error
                const loginError = document.getElementById('login-error');
                if (loginError) {
                    loginError.textContent = "<%= msg %>";
                    loginError.style.display = 'block';
                }
            <% } %>
        });
    </script>

</body>
</html>