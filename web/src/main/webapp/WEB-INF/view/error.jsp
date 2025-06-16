<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.app.web.dto.UsuarioDTO" %>
<%@ page import="static com.app.web.utils.Constantes.USUARIO_SESION" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - WikiMovies</title>
    <link rel="stylesheet" href="../../css/error.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" crossorigin="anonymous"/>
</head>
<body>
    <div class="particles" id="particles"></div>
    
    <div class="error-container">
        <%
            // Obtener información del error
            String errorCode = (String) request.getAttribute("errorCode");
            String errorType = (String) request.getAttribute("errorType");
            String mensaje = (String) request.getAttribute("mensaje");
            String userAgent = request.getHeader("User-Agent");
            String requestUrl = request.getRequestURL().toString();
            String method = request.getMethod();
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            UsuarioDTO usuario =  (UsuarioDTO) session.getAttribute(USUARIO_SESION);
            
            // Determinar tipo de error para el estilo
            String iconClass = "error";
            String titleClass = "error";
            String icon = "fas fa-exclamation-triangle";
            String title = "Error del Sistema";
            
            if (errorType != null) {
                switch (errorType.toLowerCase()) {
                    case "warning":
                        iconClass = "warning";
                        titleClass = "warning";
                        icon = "fas fa-exclamation-circle";
                        title = "Advertencia";
                        break;
                    case "info":
                        iconClass = "info";
                        titleClass = "info";
                        icon = "fas fa-info-circle";
                        title = "Información";
                        break;
                }
            }
            
            if (errorCode == null) errorCode = "ERR_UNKNOWN";
            if (mensaje == null) mensaje = "Ha ocurrido un error inesperado en el sistema.";
        %>
        
        <div class="error-icon <%= iconClass %>">
            <i class="<%= icon %>"></i>
        </div>
        
        <h1 class="error-title <%= titleClass %>"><%= title %></h1>
        
        <div class="error-code">Código: <%= errorCode %></div>

        <div class="error-message <%= iconClass %>">
            <strong>Descripción del problema:</strong><br>
            <%= mensaje %>
        </div>

        <div class="error-info-grid">
            <div class="error-info-item">
                <div class="error-info-label">Fecha y Hora</div>
                <div class="error-info-value"><%= timestamp %></div>
            </div>
            <div class="error-info-item">
                <div class="error-info-label">Método</div>
                <div class="error-info-value"><%= method %></div>
            </div>
            <div class="error-info-item">
                <div class="error-info-label">URL Solicitada</div>
                <div class="error-info-value" style="word-break: break-all; font-size: 12px;"><%= requestUrl %></div>
            </div>
            <div class="error-info-item">
                <div class="error-info-label">Sesión</div>
                
                <div class="error-info-value" style="font-family: monospace; font-size: 11px;"><%= usuario == null ? "sesion no iniciada" : usuario.getNombreUsuario() %></div>
            </div>
        </div>

        <div class="error-details">
            <div class="error-details-header" onclick="toggleDetails()">
                <span><i class="fas fa-cog"></i> Detalles Técnicos</span>
                <i class="fas fa-chevron-down" id="details-arrow"></i>
            </div>
            <div class="error-details-content" id="details-content">
                <pre>User-Agent: <%= userAgent %>
Request URL: <%= requestUrl %>
Request Method: <%= method %>
Timestamp: <%= timestamp %>
Session: <%= usuario == null ? "sesion no iniciada" : usuario.getNombreUsuario() %>
Error Code: <%= errorCode %>
Error Type: <%= errorType != null ? errorType : "ERROR" %>

Stack Trace:
<%
    Exception exception = (Exception) request.getAttribute("exception");
    if (exception != null) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        exception.printStackTrace(pw);
        out.print(sw.toString());
    } else {
        out.print("No hay información adicional del stack trace disponible.");
    }
%></pre>
            </div>
        </div>

        <p style="color: #b3b3b3; margin: 20px 0; line-height: 1.5;">
            Si el problema persiste, puedes intentar las siguientes acciones o contactar con soporte técnico.
        </p>

        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                <i class="fas fa-home"></i>
                Volver al Inicio
            </a>
            <a href="javascript:history.back()" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i>
                Página Anterior
            </a>
            <a href="javascript:location.reload()" class="btn btn-info">
                <i class="fas fa-redo"></i>
                Reintentar
            </a>
        </div>
    </div>

    <script>
        // Toggle detalles técnicos
        function toggleDetails() {
            const content = document.getElementById('details-content');
            const arrow = document.getElementById('details-arrow');
            
            if (content.classList.contains('expanded')) {
                content.classList.remove('expanded');
                arrow.style.transform = 'rotate(0deg)';
            } else {
                content.classList.add('expanded');
                arrow.style.transform = 'rotate(180deg)';
            }
        }
        
        // Crear partículas flotantes
        function createParticles() {
            const particlesContainer = document.getElementById('particles');
            const particleCount = 20;
            
            for (let i = 0; i < particleCount; i++) {
                const particle = document.createElement('div');
                particle.className = 'particle';
                particle.style.left = Math.random() * 100 + '%';
                particle.style.width = particle.style.height = (Math.random() * 3 + 1) + 'px';
                particle.style.animationDelay = Math.random() * 6 + 's';
                particle.style.animationDuration = (Math.random() * 3 + 3) + 's';
                particlesContainer.appendChild(particle);
            }
        }
        
        // Efecto de typing en el título
        function addTypingEffect() {
            const title = document.querySelector('.error-title');
            if (title) {
                title.classList.add('typing');
            }
        }
        
        // Inicializar efectos
        document.addEventListener('DOMContentLoaded', function() {
            createParticles();
            
            // Auto-colapsar detalles después de 5 segundos si están abiertos
            setTimeout(() => {
                const content = document.getElementById('details-content');
                if (content && content.classList.contains('expanded')) {
                    toggleDetails();
                }
            }, 5000);
        });
        
        // Enviar información del error al servidor para logging (opcional)
        function reportError() {
            // Implementar si se desea logging automático
            console.log('Error reportado:', {
                code: '<%= errorCode %>',
                type: '<%= errorType %>',
                message: '<%= mensaje %>',
                timestamp: '<%= timestamp %>'
            });
        }
    </script>
</body>
</html>