package com.app.web.dao;

import com.app.web.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepositorio extends JpaRepository<Usuario, Integer> {
}
