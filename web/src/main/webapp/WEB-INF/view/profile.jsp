<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.app.web.dto.ListaDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="static com.app.web.utils.Constantes.USER_ADMIN" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.app.web.dto.UsuarioDTO" %>
<%@ page import="java.util.Objects" %>

<html>

<head>
    <title> WikiMovies </title>
    <link rel="stylesheet" href="../../css/profile.css">
    <link rel="icon" type="image/png" href="../../img/favicon.png">
</head>
<body>

<%@ include file="barra_navegacion.jsp" %>

<%
    UsuarioDTO userProfile = (UsuarioDTO) request.getAttribute("usuarioProfile");
    List<ListaDTO> listasDTO = (List<ListaDTO>) request.getAttribute("listasDTO");
    Integer seguidos = (Integer) request.getAttribute("numseguidos");
%>

<div class="profile-container">
    <div class="profile-header">
        <div class="profile-avatar">
            <img src="<%= userProfile.getAvatar() != null ? userProfile.getAvatar() : "../../img/default-avatar.png"%>" alt="Avatar de <%=userProfile.getNombreUsuario()%>">
        </div>
        <div class="profile-info">
            <h1 class="profile-username"><%=userProfile.getNombreUsuario()%></h1>
            <p class="profile-bio"><%=userProfile.getGenero() != null && !userProfile.getBiografia().isEmpty() ? userProfile.getBiografia() : "Sin biografía"%></p>

            <%
                if(usuario != null && userProfile.getIdUsuario() != usuario.getIdUsuario()){
                    if(usuario.sigueA(userProfile)) { %>
                        <a class="unfollow-btn" href="/dejarSeguir?id=<%= userProfile.getIdUsuario() %>">Dejar de seguir</a>
                    <% } else { %>
                        <a class="follow-btn" href="/seguir?id=<%= userProfile.getIdUsuario() %>">Seguir</a>
                    <% } %>
            <%
                }
            %>


            <%
                if(usuario != null && Objects.equals(userProfile.getIdUsuario(), usuario.getIdUsuario()) && usuario.getRol() < 2){
            %>
            <div class="botonPremium">
                <a class="boton-premium" href="/cambioRol?id=<%= userProfile.getIdUsuario() %>">
                    <%= (userProfile.getRol() == 0)? "Hacerse Premium" : "Dejar de ser Premium"%>
                </a>
            </div>
            <%
                }
            %>

        </div>

    </div>
    
    <div class="profile-stats">
        <div class="stat-box">
            <span class="stat-count"><%= userProfile.getSeguidoresIds().size() %></span>
            <span class="stat-label">Seguidores</span>
        </div>
        <div class="stat-box">
            <span class="stat-count"><%= seguidos %></span>
            <span class="stat-label">Siguiendo</span>
        </div>
        <div class="stat-box">
            <span class="stat-count"><%= listasDTO != null ? listasDTO.size() : 0 %></span>
            <span class="stat-label">Listas</span>
        </div>
    </div>

    <div class="profile-lists">
        <h2>Listas de <%= userProfile.getNombreUsuario() %> </h2>
        <div class="lists-container">
            <% if (listasDTO != null && !listasDTO.isEmpty()) { %>
                <% for (ListaDTO lista : listasDTO) { %>
                    <a href="/mostrarLista?listaId=<%= lista.getId() %>" class="lista-link">
                    <div class="list-card">
                        <div class="list-thumbnail">
                            <img src="<%= lista.getFotoUrl() != null ? lista.getFotoUrl() : "../../img/default-list.png" %>"
                                 alt="<%= lista.getNombre() %>">
                        </div>
                        <div class="list-info">
                            <h3 class="list-title"><%= lista.getNombre() %></h3>
                            <p class="list-description"><%= lista.getDescripcion() %></p>
                            <p class="list-propietario">Usuario: <%=lista.getNombreUsuario()%></p>
                            <span class="list-count"><%= lista.getPeliculas().size() %> películas</span>
                        </div>
                    </div>
                    </a>
                <% } %>
            <% } %>
        </div>
    </div>

    <%
        if ( usuario != null && ( usuario.getRol() == USER_ADMIN || Objects.equals(usuario.getIdUsuario(), userProfile.getIdUsuario()))){
    %>
    <div class="profile-editar">
        <button id="editProfileBtn" class="edit-profile-btn">Editar perfil</button>

        <% if ( usuario.getRol() == USER_ADMIN){ %>
        <button type="button" class="btn-delete-user" onclick="showDeleteModal(<%=userProfile.getIdUsuario()%>, '<%=userProfile.getNombreUsuario()%>')">
            Eliminar perfil
        </button>
        <% } %>

        <div id="editProfileModal" class="modal">
            <div class="modal-content">
                <span class="close-modal">&times;</span>
                <h2>Editar tu perfil</h2>
                <form:form id="editProfileForm" action="/profile/update" method="POST" enctype="multipart/form-data" modelAttribute="usuarioProfile" >

                    <form:hidden path="idUsuario" />

                    <div class="form-group">
                        <label for="avatar">Foto de perfil:</label>
                        <div class="avatar-preview">
                            <img id="avatarPreview" placeholder="URL imagen" src="<%= userProfile.getAvatar() != null ? userProfile.getAvatar() : "../../img/default-avatar.png"%>" alt="Avatar">
                        </div>

                        <form:input type="text" id="avatar" path="avatar" placeholder="url" />

                    </div>

                    <div class="form-group">
                        <label for="nombreUsuario">Nombre de usuario:</label>
                        <form:input type="text" id="nombreUsuario" path="nombreUsuario" value="<%= userProfile.getNombreUsuario() %>"/>
                    </div>

                    <div class="form-group">
                        <label for="fechaNacimiento">Fecha Nacimiento:</label>
                        <form:input type="date" id="fechaNacimiento" path="nacimientoFecha"/>
                    </div>

                    <div id="generosUsuarios" class="form-group">
                        <label for="generos">Género:</label>
                        <%--@elvariable id="generosUsuarios" type="java.util.List<RolesDTO>"--%>
                        <form:radiobuttons id="generos" path="genero" itemValue="id" itemLabel="nombre" items="${generosUsuarios}"  />
                    </div>

                    <div class="form-group">
                        <label for="biografia">Biografía:</label>
                        <textarea id="biografia" name="biografia" maxlength="500" rows="7"><%= userProfile.getBiografia() != null ? userProfile.getBiografia() : "" %></textarea>
                    </div>

                    <% if ( usuario.getRol() == USER_ADMIN && !Objects.equals(usuario.getIdUsuario(), userProfile.getIdUsuario())){ %>

                    <div class="form-group">
                        <label for="rol">Rol del usuario:</label><br/>
                            <%--@elvariable id="rolesUsuarios" type="java.util.List<RolesDTO>"--%>
                            <form:radiobuttons id="rol" path="rol" itemValue="id" itemLabel="nombre" items="${rolesUsuarios}"  />
                    </div>

                    <% } %>

                    <div id="roles" class="form-actions vertical">
                        <button type="button" class="cancel-btn">Cancelar</button>
                        <form:button type="submit" class="save-btn">Guardar cambios</form:button>
                    </div>

                </form:form>
            </div>
        </div>
    </div>
    <%
        }
    %>

    <!-- Modal de confirmación de eliminación -->
    <div id="deleteUserModal" class="delete-confirmation-modal">
        <div class="delete-modal-overlay"></div>
        <div class="delete-modal-content">
            <div class="delete-modal-header">
                <h3>Eliminar Usuario</h3>
            </div>
            
            <div class="delete-modal-body">
                <p class="delete-main-text">¿Eliminar a <strong id="deleteUserName"></strong>?</p>
                <p class="delete-warning-text">Esta acción no se puede deshacer.</p>
            </div>
            
            <div class="delete-modal-footer">
                <button onclick="hideDeleteModal()" class="btn-cancel-delete">
                    Cancelar
                </button>
                <button onclick="confirmUserDeletion()" class="btn-confirm-delete">
                    Eliminar
                </button>
            </div>
        </div>
    </div>

</div>

<script>
    let userIdToDelete = null;

    function showDeleteModal(userId, userName) {
        userIdToDelete = userId;
        document.getElementById('deleteUserName').textContent = userName;
        const modal = document.getElementById('deleteUserModal');
        modal.style.display = 'block';
        document.body.style.overflow = 'hidden';
        
        // Trigger reflow para asegurar que la animación funcione
        modal.offsetHeight;
        
        setTimeout(() => {
            document.querySelector('.delete-modal-content').classList.add('show');
        }, 10);
    }

    function hideDeleteModal() {
        const modalContent = document.querySelector('.delete-modal-content');
        modalContent.classList.remove('show');
        
        setTimeout(() => {
            document.getElementById('deleteUserModal').style.display = 'none';
            document.body.style.overflow = 'auto';
        }, 400);
        
        userIdToDelete = null;
    }

    function confirmUserDeletion() {
        if (userIdToDelete) {
            // Efecto visual de confirmación
            const confirmBtn = document.querySelector('.btn-confirm-delete');
            const originalText = confirmBtn.textContent;
            confirmBtn.textContent = 'Eliminando...';
            confirmBtn.style.opacity = '0.7';
            confirmBtn.disabled = true;
            
            setTimeout(() => {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = '/eliminar?id=' + userIdToDelete;
                document.body.appendChild(form);
                form.submit();
            }, 800);
        }
    }

    // Cerrar con Escape
    document.addEventListener('keydown', function(event) {
        if (event.key === 'Escape') {
            hideDeleteModal();
        }
    });

    // Cerrar clickeando fuera
    window.onclick = function(event) {
        const modal = document.getElementById('deleteUserModal');
        if (event.target === modal || event.target.classList.contains('delete-modal-overlay')) {
            hideDeleteModal();
        }
    }

    // Prevenir cierre accidental con doble click
    document.querySelector('.delete-modal-content').addEventListener('click', function(event) {
        event.stopPropagation();
    });
</script>

<script type="text/javascript" src="../../js/updateProfile.js"></script>

</body>
</html>