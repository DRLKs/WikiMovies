package com.app.web.dao;

import com.app.web.entity.Generousuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerosUsuariosRepository extends JpaRepository<Generousuario, Integer> {
}
