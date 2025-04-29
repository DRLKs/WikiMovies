package com.app.web.ui;

import lombok.Data;

@Data
public class UsuarioLogin {
    protected String email;
    protected String password;
    protected Boolean remember;
}
