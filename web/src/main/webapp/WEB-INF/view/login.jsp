<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/signupLogin.css">

<body>
<div class="container">
    <h1>Iniciar Sesión</h1>
    <form id="login-form" method="post" action="${pageContext.request.contextPath}/log">
        <div class="form-group">
            <label for="email">Correo electrónico</label>
            <input type="email" id="email" name="email" placeholder="Ingresa tu correo electrónico" required>
            <div class="error" id="email-error">Por favor, ingresa un correo electrónico válido</div>
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="pwd" placeholder="Ingresa tu contraseña" required>
            <div class="error" id="password-error">Por favor, ingresa tu contraseña</div>
        </div>

        <div class="remember-me">
            <input type="checkbox" id="remember" name="remember">
            <label for="remember">Recordarme</label>
        </div>

        <!--

        No incorporamos olvidar contraseña en esta versión

        <div class="forgot-password">
            <a href="#">¿Olvidaste tu contraseña?</a>
        </div>
        -->
        <button type="submit">Iniciar Sesión</button>
        <div class="success-message" id="success-message">¡Sesión iniciada correctamente!</div>
        <div class="error-message" id="login-error" style="display:none; color: #e74c3c; text-align: center; margin-top: 10px;"></div>
    </form>


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