package com.app.web.ui;

import lombok.Data;

@Data
public class UsuarioSignup {
    protected String username;
    protected String email;
    protected String password;
    protected String passwordConfirmed;
}
