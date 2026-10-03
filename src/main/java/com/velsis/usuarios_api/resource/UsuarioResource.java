package com.velsis.usuarios_api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velsis.usuarios_api.entity.Usuario;
import com.velsis.usuarios_api.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/usuarios") 
public class UsuarioResource {
    
    @Autowired 
    private UsuarioService service;

    @GetMapping 
    public List<Usuario> listar() {
        return service.listar();
    }
}
