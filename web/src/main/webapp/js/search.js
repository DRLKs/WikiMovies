    function toggleFavorite(event, movieId) {
    // Stop the click from navigating to the movie page
    event.preventDefault();
    event.stopPropagation();

    // Get the button
    const button = event.currentTarget;

    // Toggle active class
    button.classList.toggle('active');

    // Send favorite status to server
    if (button.classList.contains('active')) {
    // Add to favorites
    saveFavorite(movieId, true);
} else {
    // Remove from favorites
    saveFavorite(movieId, false);
}
}

    function saveFavorite(movieId, isFavorite) {
    // Send request to server to save favorite status
    fetch('saveFavorite', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `movieId=${movieId}&favorite=${isFavorite}`
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

    // Initialize favorites (you'll need server-side logic to get user's favorites)
    document.addEventListener('DOMContentLoaded', function() {
    // Example: fetch user favorites and mark buttons as active
    // This is a placeholder - implement according to your backend
});
