document.addEventListener('DOMContentLoaded', function() {
    // Modal functionality
    const modal = document.getElementById('editProfileModal');
    const btn = document.getElementById('editProfileBtn');
    const closeBtn = document.querySelector('.close-modal');
    const cancelBtn = document.querySelector('.cancel-btn');

    if (btn) {
        btn.onclick = function() {
            modal.style.display = "block";
        }
    }

    if (closeBtn) {
        closeBtn.onclick = function() {
            modal.style.display = "none";
        }
    }

    if (cancelBtn) {
        cancelBtn.onclick = function() {
            modal.style.display = "none";
        }
    }

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }

    // Avatar preview functionality
    const avatarInput = document.getElementById('avatar');
    const avatarPreview = document.getElementById('avatarPreview');

    if (avatarInput && avatarPreview) {
        avatarInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();

                reader.onload = function(e) {
                    avatarPreview.src = e.target.result;
                }

                reader.readAsDataURL(this.files[0]);
            }
        });
    }
});

// Mantener la funcionalidad de seguir/dejar de seguir
document.querySelectorAll('.follow-btn').forEach(button => {
    button.addEventListener('click', function() {
        const userId = this.getAttribute('data-userid');
        fetch('/usuario/seguir', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `usuarioId=${userId}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    this.textContent = this.textContent === 'Seguir' ? 'Dejar de seguir' : 'Seguir';
                    // Actualizar contador de seguidores
                    const seguidoresElement = document.querySelector('.stat-count');
                    const seguidores = parseInt(seguidoresElement.textContent);
                    seguidoresElement.textContent = this.textContent === 'Dejar de seguir' ?
                        (seguidores + 1) : (seguidores - 1);
                }
            });
    });
});