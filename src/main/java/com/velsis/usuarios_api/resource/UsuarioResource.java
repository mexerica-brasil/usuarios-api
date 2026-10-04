package com.velsis.usuarios_api.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.velsis.usuarios_api.entity.Usuario;
import com.velsis.usuarios_api.service.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;



@RestController 
@RequestMapping("/usuarios") 
public class UsuarioResource {
    
    @Autowired 
    private UsuarioService service;

    @GetMapping 
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> criar(@Valid  @RequestBody Usuario usuario, HttpServletResponse response) {
        Usuario usuarioSalvo = service.incluir(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{cpf}").buildAndExpand(usuarioSalvo.getCpf()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(usuarioSalvo);
    }

    @GetMapping("/{cpf}") 
    public ResponseEntity<Usuario>  recuperarPeloCpf(@PathVariable String cpf) {
        Usuario usuario = service.recuperarPeloCpf(cpf);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build(); 
    }

    @DeleteMapping("/{id}") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer id) {
        service.exluir(id);
    }

    @PutMapping
    public Usuario atualizar(@Valid @RequestBody Usuario usuario) {
        return service.alterar(usuario);
    }
}