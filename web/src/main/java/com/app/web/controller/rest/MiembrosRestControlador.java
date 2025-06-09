package com.app.web.controller.rest;

import com.app.web.dto.UsuarioDTO;
import com.app.web.service.MiembrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/miembros")
public class MiembrosRestControlador {

    @Autowired private MiembrosService miembrosService;

    @GetMapping("/")
    public List<UsuarioDTO> obtenerTodosLosMiembros(){
        return miembrosService.obtenerMiembros(null);
    }

    @GetMapping("/{id}")
    public UsuarioDTO obtenerUsuarioPorId(@PathVariable Integer id){
        return miembrosService.obtenerUsuario(id);
    }

    @GetMapping("/filtrar/{nombre}")
    public List<UsuarioDTO> obtenerUsuarioPorNombre(@PathVariable String nombre){
        return miembrosService.obtenerMiembros(nombre);
    }

    @PutMapping("/")
    public void editarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        miembrosService.editarUsuario(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public void borrarUsuario(@PathVariable Integer id){
        miembrosService.eliminarUsuario(id);
    }

}
