package com.app.web.service;

import com.app.web.dao.GenerosUsuariosRepository;
import com.app.web.dao.RolesRepository;
import com.app.web.dao.UsuariosRepositorio;
import com.app.web.dto.UsuarioDTO;
import com.app.web.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MiembrosService extends DTOService<UsuarioDTO, Usuario> {

    @Autowired private UsuariosRepositorio usuariosRepositorio;
    @Autowired private GenerosUsuariosRepository generosUsuariosRepository;
    @Autowired private RolesRepository rolesRepository;

    public List<UsuarioDTO> obtenerMiembros(String filtroNombre){

        if( filtroNombre == null ){
            return this.entity2DTO(usuariosRepositorio.findAll());
        }else{
            return this.entity2DTO(usuariosRepositorio.findByNombre(filtroNombre));
        }

    }

    public UsuarioDTO obtenerUsuario(Integer id){

        Usuario usuario = usuariosRepositorio.getReferenceById(id);
        return usuario.toDTO();
    }

    public void editarUsuario(UsuarioDTO usuarioProfile){

        // Obtenemos el usuario que queremos editar
        Usuario usuario = usuariosRepositorio.getReferenceById(usuarioProfile.getIdUsuario());

        // Tomamos los datos obtenidos del formulario
        String avatar = usuarioProfile.getAvatar();
        String nombreUsuario = usuarioProfile.getNombreUsuario();
        String biografia = usuarioProfile.getBiografia();
        LocalDate fechaNacimiento = usuarioProfile.getNacimientoFecha();
        Integer genero = usuarioProfile.getGenero();
        Integer rol = usuarioProfile.getRol();


        // Realizamos los cambios
        try {

            if (avatar != null && !avatar.isEmpty()) {
                usuario.setAvatarUrl(avatar);
            }

            if (nombreUsuario != null && !nombreUsuario.isEmpty()
                    && !nombreUsuario.equals(usuario.getNombreUsuario())) {
                usuario.setNombreUsuario(nombreUsuario);
            }

            if (biografia != null && !biografia.equals(usuario.getBiografia())) {
                usuario.setBiografia(biografia);
            }

            if (fechaNacimiento != null) {
                usuario.setNacimientoFecha(fechaNacimiento);
            }

            if (genero != null) {
                usuario.setGenero(generosUsuariosRepository.getReferenceById(genero));
            }

            if( rol != null ){
                usuario.setRol(rolesRepository.getReferenceById(rol));
            }

        } catch (Exception e) {
            // No se lanza nada
            // Sigue como si no hubiera pasado nada
        }

        this.usuariosRepositorio.save(usuario);

    }

    public void eliminarUsuario(Integer idUsuario){
        Usuario usuario = usuariosRepositorio.getReferenceById(idUsuario);
        usuariosRepositorio.delete(usuario);
    }


    //
    // MANEJADOR SOBRE SEGUIDORES-SEGUIDOS
    //

    public void seguirUsuario(Integer miId, Integer idUsuario){

        Usuario miUsuario = usuariosRepositorio.getReferenceById(miId);
        Usuario usuarioSeguir = usuariosRepositorio.getReferenceById(idUsuario);

        usuarioSeguir.getSeguidores().add(miUsuario);

        usuariosRepositorio.save(usuarioSeguir);
    }

    public void dejarDeSeguirUsuario(Integer miId, Integer idUsuario){

        Usuario miUsuario = usuariosRepositorio.getReferenceById(miId);
        Usuario usuarioSeguir = usuariosRepositorio.getReferenceById(idUsuario);

        usuarioSeguir.getSeguidores().remove(miUsuario);

        usuariosRepositorio.save(usuarioSeguir);
    }

    /**
     * Número de personas que sigue el usuario
     * @param id Identificador del usaurio
     * @return Integer
     */
    public Integer numSeguidosUsuario(Integer id){
        return usuariosRepositorio.getNumSeguidos(id);
    }


    public void editarRolPremium(Integer id) {
        // Obtenemos el usuario que queremos editar
        Usuario usuario = usuariosRepositorio.getReferenceById(id);

        int rol = usuario.getRol().getId();

        usuario.setRol( rolesRepository.getReferenceById( (rol + 1) % 2) );

        this.usuariosRepositorio.save(usuario);
    }

}
