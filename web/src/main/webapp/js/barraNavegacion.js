/* Script para mostrar/ocultar filtros */

document.addEventListener('DOMContentLoaded', function() {
    const filtersToggle = document.querySelector('.filters-toggle');
    const filters = document.querySelector('.filters');

    filtersToggle.addEventListener('click', function() {
    const isHidden = filters.style.display === 'none';
    filters.style.display = isHidden ? 'block' : 'none';
    filtersToggle.textContent = isHidden ? 'Ocultar filtros avanzados' : 'Mostrar filtros avanzados';
    });
});
