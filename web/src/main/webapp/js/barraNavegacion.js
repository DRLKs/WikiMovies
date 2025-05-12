/* Script para mostrar/ocultar filtros */

document.addEventListener('DOMContentLoaded', function() {
    // Seleccionar elementos
    const filterToggle = document.querySelector('.filters-toggle');
    const filtersPanel = document.querySelector('.filters');
    
    // Función para mostrar/ocultar el panel de filtros
    function toggleFilters(event) {
        event.stopPropagation(); // Evitar que el clic se propague
        const isVisible = filtersPanel.style.display === 'block';
        filtersPanel.style.display = isVisible ? 'none' : 'block';
    }
    
    // Listener para el botón de filtros
    if (filterToggle) {
        filterToggle.addEventListener('click', toggleFilters);
    }
    
    // Cerrar el panel de filtros al hacer clic en cualquier parte fuera de él
    document.addEventListener('click', function(event) {
        if (filtersPanel.style.display === 'block' && 
            !filtersPanel.contains(event.target) && 
            event.target !== filterToggle) {
            filtersPanel.style.display = 'none';
        }
    });
    
    // Evitar que los clics dentro del panel cierren el panel
    if (filtersPanel) {
        filtersPanel.addEventListener('click', function(event) {
            event.stopPropagation();
        });
    }
});
