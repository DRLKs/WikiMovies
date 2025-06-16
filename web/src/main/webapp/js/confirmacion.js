let listIdToDelete = null;
let movieIdToDelete = null;
let listIdForMovie = null;

// Funciones para eliminar lista
function showDeleteListModal(listId, listName) {
    listIdToDelete = listId;
    document.getElementById('deleteListName').textContent = listName;
    const modal = document.getElementById('deleteListModal');
    modal.style.display = 'block';
    document.body.style.overflow = 'hidden';

    modal.offsetHeight;

    setTimeout(() => {
        modal.querySelector('.delete-modal-content').classList.add('show');
    }, 10);
}

function hideDeleteListModal() {
    const modalContent = document.querySelector('#deleteListModal .delete-modal-content');
    modalContent.classList.remove('show');

    setTimeout(() => {
        document.getElementById('deleteListModal').style.display = 'none';
        document.body.style.overflow = 'auto';
    }, 400);

    listIdToDelete = null;
}

// Funciones para eliminar película de lista
function showDeleteMovieModal(movieId, movieName, listId) {
    movieIdToDelete = movieId;
    listIdForMovie = listId;
    document.getElementById('deleteMovieName').textContent = movieName;
    const modal = document.getElementById('deleteMovieModal');
    modal.style.display = 'block';
    document.body.style.overflow = 'hidden';

    modal.offsetHeight;

    setTimeout(() => {
        modal.querySelector('.delete-modal-content').classList.add('show');
    }, 10);
}

function hideDeleteMovieModal() {
    const modalContent = document.querySelector('#deleteMovieModal .delete-modal-content');
    modalContent.classList.remove('show');

    setTimeout(() => {
        document.getElementById('deleteMovieModal').style.display = 'none';
        document.body.style.overflow = 'auto';
    }, 400);

    movieIdToDelete = null;
    listIdForMovie = null;
}

function confirmMovieDeletion() {
    if (movieIdToDelete && listIdForMovie) {
        const confirmBtn = document.querySelector('#deleteMovieModal .btn-confirm-delete');
        const originalText = confirmBtn.textContent;
        confirmBtn.textContent = 'Eliminando...';
        confirmBtn.style.opacity = '0.7';
        confirmBtn.disabled = true;

        setTimeout(() => {
            window.location.href = '/quitarPeliLista?idPeli=' + movieIdToDelete + '&idLista=' + listIdForMovie;
        }, 800);
    }
}

// Cerrar con Escape
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        hideDeleteListModal();
        hideDeleteMovieModal();
    }
});

// Cerrar clickeando fuera
window.onclick = function(event) {
    const listModal = document.getElementById('deleteListModal');
    const movieModal = document.getElementById('deleteMovieModal');

    if (event.target === listModal || event.target.classList.contains('delete-modal-overlay')) {
        hideDeleteListModal();
    }
    if (event.target === movieModal || event.target.classList.contains('delete-modal-overlay')) {
        hideDeleteMovieModal();
    }
}

// Prevenir cierre accidental con doble click
document.querySelectorAll('.delete-modal-content').forEach(content => {
    content.addEventListener('click', function(event) {
        event.stopPropagation();
    });
});