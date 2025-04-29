package com.app.web.ui;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioProfile {
    protected String nombreUsuario;
    protected String avatar;
    protected String apellido;
    protected String biografia;
    protected Integer genero;
    protected LocalDate fechaNacimiento;
}
