function changeTab(event, tabName) {
    // Eliminar la clase 'active' de todas las pestañas
    document.querySelectorAll(".tab").forEach(tab => {
        tab.classList.remove("active");
    });
    
    // Agregar la clase 'active' a la pestaña seleccionada
    event.target.classList.add("active");
}

document.querySelector(".search-btn").addEventListener("click", function() {
    const query = document.getElementById("search-input").value;
    if (query.trim() !== "") {
        alert("Buscando: " + query);
    }
});
