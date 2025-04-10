package com.app.web.dao;

import com.app.web.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuariosRepositorio extends JpaRepository<Usuario, Integer> {

    @Query("select COUNT(u) > 0 from Usuario u where u.correoElectronico = :correoElectronico")
    boolean existsBycorreoElectronico(@Param("correoElectronico") String correoElectronico);

    @Query("select COUNT(u) > 0 from Usuario u where u.nombreUsuario = :nombreUsuario")
    boolean existsByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("select u from Usuario u where u.correoElectronico = :correoElectronico and u.contrasenaHash = :contrasenaHash")
    Usuario autenticaUsuario(@Param("correoElectronico") String correoElectronico, @Param("contrasenaHash") String contrasenaHash);
}
