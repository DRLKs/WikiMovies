package com.app.web.dao;

import com.app.web.entity.Lista;
import com.app.web.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuariosRepositorio extends JpaRepository<Usuario, Integer> {

    @Query("select COUNT(u) > 0 from Usuario u where u.correoElectronico = :correoElectronico")
    boolean existsBycorreoElectronico(@Param("correoElectronico") String correoElectronico);

    @Query("select COUNT(u) > 0 from Usuario u where u.nombreUsuario = :nombreUsuario")
    boolean existsByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("select u from Usuario u where u.correoElectronico = :correoElectronico and u.contrasenaHash = :contrasenaHash")
    Usuario autenticaUsuario(@Param("correoElectronico") String correoElectronico, @Param("contrasenaHash") String contrasenaHash);

    @Query("select u from Usuario u join u.seguidores s where s.id = :id")
    List<Usuario> getSeguidos(@Param("id") Integer id);

    @Query("SELECT l FROM Usuario u JOIN u.listas l WHERE u.id = :idUsuario AND l.nombre = 'Favoritas'")
    Lista getListaFavoritas(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT l FROM Usuario u JOIN u.listas l WHERE u.id = :idUsuario AND l.nombre = 'Vistas'")
    Lista getListaVistas(@Param("idUsuario") Integer idUsuario);

    Usuario getUsuarioById(Integer id);
}
