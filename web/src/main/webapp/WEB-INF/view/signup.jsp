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

<div class="container">
    <div class="mensaje-error-container">
        <%= msgError %>
    </div>
    <h1>Crear Cuenta</h1>
    <form action="/crearCuenta" method="post" id="signup-form">
        <div class="form-group">
            <label for="username">Nombre de usuario</label>
            <input type="text" id="username" name="username" placeholder="Ingresa tu nombre de usuario" required>
            <div class="error" id="username-error">El nombre de usuario debe tener al menos 4 caracteres</div>
        </div>

        <div class="form-group">
            <label for="email">Correo electrónico</label>
            <input type="email" id="email" name="email" placeholder="Ingresa tu correo electrónico" required>
            <div class="error" id="email-error">Por favor, ingresa un correo electrónico válido</div>
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="pwd" placeholder="Ingresa tu contraseña" required>
            <div class="error" id="password-error">La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula y un número</div>
        </div>

        <div class="form-group">
            <label for="confirm-password">Confirmar contraseña</label>
            <input type="password" id="confirm-password" name="pwd_confirm" placeholder="Confirma tu contraseña" required>
            <div class="error" id="confirm-password-error">Las contraseñas no coinciden</div>
        </div>

        <button type="submit">Registrarse</button>
        <div class="success-message" id="success-message">¡Cuenta creada con éxito!</div>
    </form>

    <div class="footer">
        ¿Ya tienes una cuenta?
        <a href="login" >Iniciar sesión</a>
    </div>
</div>

<script src="../../js/login.js"></script>

</body>
</html>