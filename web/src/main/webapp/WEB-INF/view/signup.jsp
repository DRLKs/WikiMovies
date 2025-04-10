<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/signup.css">

<body>
<div class="container">
    <h1>Crear Cuenta</h1>
    <form id="signup-form">
        <div class="form-group">
            <label for="username">Nombre de usuario</label>
            <input type="text" id="username" placeholder="Ingresa tu nombre de usuario" required>
            <div class="error" id="username-error">El nombre de usuario debe tener al menos 4 caracteres</div>
        </div>

        <div class="form-group">
            <label for="email">Correo electrónico</label>
            <input type="email" id="email" placeholder="Ingresa tu correo electrónico" required>
            <div class="error" id="email-error">Por favor, ingresa un correo electrónico válido</div>
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" placeholder="Ingresa tu contraseña" required>
            <div class="error" id="password-error">La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula y un número</div>
        </div>

        <div class="form-group">
            <label for="confirm-password">Confirmar contraseña</label>
            <input type="password" id="confirm-password" placeholder="Confirma tu contraseña" required>
            <div class="error" id="confirm-password-error">Las contraseñas no coinciden</div>
        </div>

        <button type="submit">Registrarse</button>
        <div class="success-message" id="success-message">¡Cuenta creada con éxito!</div>
    </form>

    <div class="footer">
        <form action="/" method="get">
            <p>¿Ya tienes una cuenta?</p>
            <button type="submit">Iniciar sesión</button>
        </form>
    </div>
</div>
</body>
</html>