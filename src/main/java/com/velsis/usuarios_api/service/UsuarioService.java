package com.velsis.usuarios_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.velsis.usuarios_api.entity.Usuario;
import com.velsis.usuarios_api.repository.UsuarioRepository;

@Service 
public class UsuarioService {
    
    @Autowired 
    private MessageSource message;

    @Autowired 
    private UsuarioRepository repository;

    public List<Usuario> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public void validarUsuario(Usuario usuario) {

    }
}
