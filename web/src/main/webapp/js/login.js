// Script para manejo del formulario de inicio de sesión
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');

    // Si no existe el formulario de login en esta página, salir
    if (!loginForm) return;

    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const successMessage = document.getElementById('success-message');
    const loginError = document.getElementById('login-error');

    // Mostrar errores
    function showError(input, message) {
        const errorElement = document.getElementById(`${input.id}-error`);
        if (errorElement) {
            errorElement.textContent = message;
            errorElement.style.display = 'block';
            input.style.borderColor = '#e74c3c';
        }
    }

    // Ocultar errores
    function hideError(input) {
        const errorElement = document.getElementById(`${input.id}-error`);
        if (errorElement) {
            errorElement.style.display = 'none';
            input.style.borderColor = '#ddd';
        }
    }

    // Mostrar mensaje de error general
    function showLoginError(message) {
        if (loginError) {
            loginError.textContent = message;
            loginError.style.display = 'block';
        }
    }

    // Ocultar mensaje de error general
    function hideLoginError() {
        if (loginError) {
            loginError.style.display = 'none';
        }
    }

    // Validar email
    function validateEmail() {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email.value)) {
            showError(email, 'Por favor, ingresa un correo electrónico válido');
            return false;
        }
        hideError(email);
        return true;
    }

    // Validar contraseña
    function validatePassword() {
        if (password.value.trim() === '') {
            showError(password, 'Por favor, ingresa tu contraseña');
            return false;
        }
        hideError(password);
        return true;
    }
    // Validar en tiempo real
    if (email) email.addEventListener('input', validateEmail);
    if (password) password.addEventListener('input', validatePassword);
});

// Scripts Registro de Usuarios
document.addEventListener('DOMContentLoaded', function() {
    const signupForm = document.getElementById('signup-form');

    // Si no existe el formulario de registro en esta página, salir
    if (!signupForm) return;

    // Resto del código de registro...
    // (mantengo el código existente para el registro)
});

