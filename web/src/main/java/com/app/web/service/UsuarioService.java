package com.app.web.service;

import com.app.web.dao.ListaRepository;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.entity.Lista;
import com.app.web.entity.Usuario;
import com.app.web.utils.Hash;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.app.web.utils.Constantes.LISTA_FAVORITAS;
import static com.app.web.utils.Constantes.LISTA_VISTAS;

public class UsuarioService {

    @Autowired private UsuariosRepositorio usuarioRepositorio;
    @Autowired private ListaRepository listaRepository;

    // Mapa simple para cachear sesiones (en producción usar algo más robusto como Redis)
    private static final Map<String, Usuario> sesionesCache = new ConcurrentHashMap<>();

    protected boolean estaAutenticado(HttpServletRequest request, HttpSession session) {

        boolean autenticado = false;
        if( session.getAttribute("usuario") != null ) {
            autenticado =  true;
        }else{
            Usuario usuario = verificarCookieSesion(request);

            if( usuario != null ) {
                session.setAttribute("usuario", usuario);
                autenticado = true;
            }
        }

        return autenticado;
    }

    protected Usuario verificarCookieSesion(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("wikimovies_user_session".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    // Si no hay usuario en la sesión, buscamos en el caché por el token
                    Usuario usuarioCached = obtenerUsuarioDesdeSesionCache(token);
                    if (usuarioCached != null) {
                        // Si encontramos al usuario, lo guardamos en la sesión actual
                        // para futuras solicitudes
                        return usuarioCached;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Método para guardar un usuario en el cache de sesiones
     * @param token Identificador del usuario en la caché
     * @param usuario Usuario a guardar
     */
    protected void guardarUsuarioEnSesionCache(String token, Usuario usuario) {
        sesionesCache.put(token, usuario);
    }

    /**
     * Método para eliminar un usuario del cache de sesiones
     * @param token Identificador del usuario en la caché
     */
    protected void eliminarUsuarioDelSesionCache(String token) {
        sesionesCache.remove(token);
    }

    /**
     * Método para recuperar usuario desde el cache de sesiones
     * @param token Identificador del usuario en la caché
     * @return Usuario obtenido en la caché
     */
    private Usuario obtenerUsuarioDesdeSesionCache(String token) {
        return sesionesCache.get(token);
    }

    public String crearCuenta(String nombreUsuario, String correoElectronico, String contrasena,
                                Model model) {
        if( this.usuarioRepositorio.existsByNombreUsuario(nombreUsuario) ){
            String msg = "El usuario indicado ya está en eso";
            model.addAttribute("mensaje", msg);
            return "signup";
        }

        if( this.usuarioRepositorio.existsBycorreoElectronico(correoElectronico) ){
            String msg = "El email indicado ya está registrado";
            model.addAttribute("mensaje", msg);
            return "signup";
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setContrasenaHash( Hash.obtenerSHA256(contrasena) );
        usuario.setBiografia(""); //añadido
        usuario.setCreacionCuentaFecha(LocalDate.now());

        this.usuarioRepositorio.save(usuario);


        //Le añado la lista de "Favoritas" y "Vistas"
        Set<Lista> listas = new HashSet<>();
        Lista listaFav = new Lista();
        listaFav.setNombre(LISTA_FAVORITAS);
        listaFav.setDescripcion("Lista de películas favoritas");
        listaFav.setImgURL("https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg=");
        listaFav.setIdUsuario(usuario);
        listaFav.setPeliculas(new HashSet<>());
        listas.add(listaFav);

        Lista listaVistas = new Lista();
        listaVistas.setNombre(LISTA_VISTAS);
        listaVistas.setDescripcion("Lista de películas vistas");
        listaVistas.setImgURL("https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg");
        listaVistas.setIdUsuario(usuario);
        listaVistas.setPeliculas(new HashSet<>());
        listas.add(listaVistas);

        this.listaRepository.save(listaVistas);
        this.listaRepository.save(listaFav);

        usuario.setListas(listas);
        this.usuarioRepositorio.save(usuario);

        return "redirect:/login";
    }
}
