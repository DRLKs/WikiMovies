

/*
falta por añadir la informacion al controlador

Esto esta raro lña verdad, revisar
@PostMapping("/likeLista")
@ResponseBody
public ResponseEntity<?> likeLista(
        @RequestParam Long usuarioId,
        @RequestParam Long listaId,
        @RequestParam boolean liked) {

    // Aquí iría la lógica para guardar/eliminar el "like" en la base de datos
    // Por ejemplo: listaService.toggleLike(usuarioId, listaId, liked);

    return ResponseEntity.ok().build();
}

 */

function toggleLike(button) {
    const img = button.querySelector("img");
    const liked = img.getAttribute("data-liked") === "true";
    const usuarioId = button.getAttribute("data-usuario-id");
    const listaId = button.getAttribute("data-lista-id");

    // Alternar imagen y estado
    if (liked) {
        img.src = "../img/corazon_vacio.png";
        img.setAttribute("data-liked", "false");
    } else {
        img.src = "../img/corazon_lleno.png";
        img.setAttribute("data-liked", "true");
    }

    // Enviar al backend
    fetch("/likeLista", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `usuarioId=${usuarioId}&listaId=${listaId}&liked=${!liked}`
    })
        .then(response => {
            if (!response.ok) {
                console.error("Error al actualizar el like");
            }
        });
}