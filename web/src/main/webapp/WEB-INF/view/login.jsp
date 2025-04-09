<%@ page import="java.util.List" %>
<%@ page import="com.app.web.dao.UsuariosRepositorio" %>
<%@ page import="com.app.web.entity.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="css/login.css">
</head>
    <%
        List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    %>
<body>
<!-- Nota: El "FONDO EPIQUISIMO CON PELÍCULAS" se configura en el CSS -->

<div class="main-container">
    <h1>WikiMovies</h1>

    <div class="login-box">
        <form action="#" method="post"> <!-- El action="#" es un placeholder -->
            <h2>Iniciar Sesión</h2>

            <p class="signup-link">
                ¿Nuevo? <a href="#">crear Cuenta</a>
            </p>

            <div class="input-group">
                <label for="email">email*</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="input-group">
                <label for="password">contraseña*</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="remember-me">
                <input type="checkbox" id="remember" name="remember">
                <label for="remember">Recuerdame</label>
            </div>

            <button type="submit" class="login-button">Iniciar Sesión</button>
        </form>
    </div>
</div>

</body>
</html>
