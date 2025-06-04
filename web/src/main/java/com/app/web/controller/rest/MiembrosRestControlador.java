package com.app.web.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/miembros")
public class MiembrosRestControlador {

    @GetMapping("/")
    public String miembros(){
        return "";
    }


}
