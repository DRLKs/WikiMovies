// Script para scroll horizontal con botones en las listas de películas
function scrollLista(btn, dir) {
    const wrapper = btn.closest('.lista-peliculas-wrapper');
    const lista = wrapper.querySelector('.lista-peliculas');
    if (!lista) return;
    // Calcula el ancho de una película (incluyendo margen)
    const item = lista.querySelector('img, a');
    let scrollBy = 200; // fallback
    if (item) {
        const style = window.getComputedStyle(item);
        scrollBy = item.offsetWidth + parseInt(style.marginLeft) + parseInt(style.marginRight);
    }
    lista.scrollBy({ left: dir * scrollBy * 2, behavior: 'smooth' });
}

// Mostrar/ocultar los botones de scroll solo si hay overflow
function updateScrollBtns() {
    document.querySelectorAll('.lista-peliculas-wrapper').forEach(wrapper => {
        const lista = wrapper.querySelector('.lista-peliculas');
        const btnLeft = wrapper.querySelector('.scroll-btn-left');
        const btnRight = wrapper.querySelector('.scroll-btn-right');
        if (!lista || !btnLeft || !btnRight) return;
        // Mostrar solo si hay overflow
        if (lista.scrollWidth > lista.clientWidth + 2) {
            btnLeft.style.display = 'flex';
            btnRight.style.display = 'flex';
        } else {
            btnLeft.style.display = 'none';
            btnRight.style.display = 'none';
        }
    });
}
window.addEventListener('DOMContentLoaded', updateScrollBtns);
window.addEventListener('resize', updateScrollBtns);


// Mostrar/ocultar los botones de scroll solo si hay overflow y según la posición de scroll
function updateScrollBtns() {
    document.querySelectorAll('.lista-peliculas-wrapper').forEach(wrapper => {
        const lista = wrapper.querySelector('.lista-peliculas');
        const btnLeft = wrapper.querySelector('.scroll-btn-left');
        const btnRight = wrapper.querySelector('.scroll-btn-right');
        if (!lista || !btnLeft || !btnRight) return;
        // Mostrar solo si hay overflow
        if (lista.scrollWidth > lista.clientWidth + 2) {
            // Oculta el botón izquierdo si ya está al inicio
            if (lista.scrollLeft <= 0) {
                btnLeft.style.display = 'none';
            } else {
                btnLeft.style.display = 'flex';
            }
            // Oculta el botón derecho si ya está al final
            if (lista.scrollLeft + lista.clientWidth >= lista.scrollWidth - 2) {
                btnRight.style.display = 'none';
            } else {
                btnRight.style.display = 'flex';
            }
        } else {
            btnLeft.style.display = 'none';
            btnRight.style.display = 'none';
        }
    });
}
window.addEventListener('DOMContentLoaded', updateScrollBtns);
window.addEventListener('resize', updateScrollBtns);
document.addEventListener('scroll', updateScrollBtns, true);
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.lista-peliculas').forEach(lista => {
        lista.addEventListener('scroll', updateScrollBtns);
    });
});