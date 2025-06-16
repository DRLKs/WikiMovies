// Script para scroll horizontal con botones en las listas de películas
function scrollLista(button, direction) {
    const wrapper = button.closest('.lista-peliculas-wrapper');
    const lista = wrapper.querySelector('.lista-peliculas');
    const scrollAmount = 600; // Cantidad de píxeles a desplazar
    
    if (direction === -1) {
        // Scroll hacia la izquierda
        lista.scrollBy({
            left: -scrollAmount,
            behavior: 'smooth'
        });
    } else {
        // Scroll hacia la derecha
        lista.scrollBy({
            left: scrollAmount,
            behavior: 'smooth'
        });
    }
    
    // Actualizar botones después del scroll
    setTimeout(() => {
        updateScrollButtons();
    }, 100);
}

// Función para mostrar/ocultar botones según el contenido y posición de scroll
function updateScrollButtons() {
    document.querySelectorAll('.lista-peliculas-wrapper').forEach(wrapper => {
        const lista = wrapper.querySelector('.lista-peliculas');
        const leftBtn = wrapper.querySelector('.scroll-btn-left');
        const rightBtn = wrapper.querySelector('.scroll-btn-right');
        
        // Verificar si hay suficiente contenido para hacer scroll
        const needsScroll = lista.scrollWidth > lista.clientWidth + 10; // +10 para margen de error
        
        if (!needsScroll) {
            // No hay contenido suficiente, ocultar todos los botones
            wrapper.classList.add('no-scroll');
            leftBtn.classList.remove('visible');
            rightBtn.classList.remove('visible');
            return;
        }
        
        wrapper.classList.remove('no-scroll');
        
        // Verificar posición de scroll para mostrar/ocultar botones específicos
        const scrollLeft = lista.scrollLeft;
        const maxScrollLeft = lista.scrollWidth - lista.clientWidth;
        
        // Botón izquierdo: mostrar solo si no estamos al principio
        if (scrollLeft > 5) { // 5px de margen
            leftBtn.classList.add('visible');
        } else {
            leftBtn.classList.remove('visible');
        }
        
        // Botón derecho: mostrar solo si no estamos al final
        if (scrollLeft < maxScrollLeft - 5) { // 5px de margen
            rightBtn.classList.add('visible');
        } else {
            rightBtn.classList.remove('visible');
        }
    });
}

// Hacer las listas desplazables con el mouse/touch
document.addEventListener('DOMContentLoaded', function() {
    // Actualizar botones al cargar
    setTimeout(updateScrollButtons, 200); // Delay para asegurar que las imágenes se carguen
    
    document.querySelectorAll('.lista-peliculas').forEach(lista => {
        let isDown = false;
        let startX;
        let scrollLeft;

        // Agregar listener para detectar cambios en el scroll
        lista.addEventListener('scroll', () => {
            updateScrollButtons();
        });

        // Mouse events
        lista.addEventListener('mousedown', (e) => {
            isDown = true;
            lista.classList.add('active');
            startX = e.pageX - lista.offsetLeft;
            scrollLeft = lista.scrollLeft;
            lista.style.cursor = 'grabbing';
        });

        lista.addEventListener('mouseleave', () => {
            isDown = false;
            lista.classList.remove('active');
            lista.style.cursor = 'grab';
        });

        lista.addEventListener('mouseup', () => {
            isDown = false;
            lista.classList.remove('active');
            lista.style.cursor = 'grab';
        });

        lista.addEventListener('mousemove', (e) => {
            if (!isDown) return;
            e.preventDefault();
            const x = e.pageX - lista.offsetLeft;
            const walk = (x - startX) * 2;
            lista.scrollLeft = scrollLeft - walk;
        });

        // Touch events para móviles
        let startTouchX;
        let scrollStartLeft;

        lista.addEventListener('touchstart', (e) => {
            startTouchX = e.touches[0].pageX - lista.offsetLeft;
            scrollStartLeft = lista.scrollLeft;
        });

        lista.addEventListener('touchmove', (e) => {
            if (!startTouchX) return;
            e.preventDefault();
            const x = e.touches[0].pageX - lista.offsetLeft;
            const walk = (x - startTouchX) * 2;
            lista.scrollLeft = scrollStartLeft - walk;
        });

        lista.addEventListener('touchend', () => {
            startTouchX = null;
            // Actualizar botones después del touch
            setTimeout(updateScrollButtons, 100);
        });

        // Estilo inicial
        lista.style.cursor = 'grab';
    });
});

// Actualizar botones cuando se redimensiona la ventana
window.addEventListener('resize', () => {
    setTimeout(updateScrollButtons, 100);
});

// Actualizar botones cuando se cargan las imágenes
window.addEventListener('load', () => {
    setTimeout(updateScrollButtons, 300);
});

// Observer para detectar cambios en el contenido
const observer = new MutationObserver(() => {
    setTimeout(updateScrollButtons, 150);
});

document.addEventListener('DOMContentLoaded', () => {
    const container = document.querySelector('.peliculas-container');
    if (container) {
        observer.observe(container, {
            childList: true,
            subtree: true
        });
    }
});