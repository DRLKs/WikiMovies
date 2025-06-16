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