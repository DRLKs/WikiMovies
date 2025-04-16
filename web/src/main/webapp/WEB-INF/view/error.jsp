<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - WikiMovies</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #333;
        }

        .error-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            padding: 40px;
            text-align: center;
            max-width: 500px;
            width: 90%;
        }

        .error-icon {
            font-size: 60px;
            color: #e74c3c;
            margin-bottom: 20px;
        }

        h1 {
            margin-top: 0;
            color: #e74c3c;
        }

        .error-message {
            margin: 30px 0;
            padding: 15px;
            background-color: #f8f8f8;
            border-radius: 4px;
            border-left: 4px solid #e74c3c;
            text-align: left;
        }

        .btn {
            display: inline-block;
            background-color: #3498db;
            color: white;
            padding: 12px 24px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-icon">&#9888;</div>
    <h1>Ha ocurrido un error</h1>

    <div class="error-message">
        ${mensaje != null ? mensaje : "No se ha podido completar la operación solicitada."}
    </div>

    <p>Por favor, inténtelo de nuevo más tarde o vuelva a la página principal.</p>

    <a href="${pageContext.request.contextPath}/" class="btn">Volver al inicio</a>
</div>
</body>
</html>