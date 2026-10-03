package com.velsis.usuarios_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.velsis.usuarios_api.entity.Endereco;
import com.velsis.usuarios_api.entity.Usuario;
import com.velsis.usuarios_api.exception.EnderecoCepDuplicadoException;
import com.velsis.usuarios_api.exception.EnderecoLogradouroDuplicadoException;
import com.velsis.usuarios_api.exception.UsuarioCpfDuplicadoException;
import com.velsis.usuarios_api.repository.EnderecoRepository;
import com.velsis.usuarios_api.repository.UsuarioRepository;

@Service 
public class UsuarioService {
    
    @Autowired 
    private MessageSource message;

    @Autowired 
    private UsuarioRepository repository;

    @Autowired 
    private EnderecoRepository repoEndereco;

    public List<Usuario> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Usuario incluir(Usuario usuario) {
        this.validarUsuario(usuario);
        usuario.setEndereco(this.confirmarEndereco(usuario));
        
        return repository.save(usuario);
    }

    public Usuario recuperarPeloCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    private void validarUsuario(Usuario usuario) {
        this.validarCpf(usuario);    
        this.validarEndereco(usuario);    
    }

    private void validarCpf(Usuario usuario) {
        Usuario usuarioCpf = repository.findByCpf(usuario.getCpf());

        if (usuarioCpf != null && !usuarioCpf.getId().equals(usuario.getId())) {
            throw new UsuarioCpfDuplicadoException(message.getMessage("service.usuario.ja.cadastrado", 
                                                                        new Object[] {usuarioCpf.getNome(), usuario.getCpf()},                                    
                                                                        LocaleContextHolder.getLocale()));
        }
    }

    private void validarCpfValido() {
        
    }

    private void validarEndereco(Usuario usuario) {
        this.validarEnderecoCepExistente(usuario);
        this.validarEnderecoCepInexistente(usuario);
    }

    private void validarEnderecoCepExistente(Usuario usuario) {
        Endereco endereco = usuario.getEndereco();
        Endereco enderecoCep = repoEndereco.findByCep(usuario.getEndereco().getCep());

        if (enderecoCep != null) {
            if (!endereco.getLogradouro().equals(enderecoCep.getLogradouro()) || !endereco.getCidade().equals(enderecoCep.getCidade())) {
                throw new EnderecoCepDuplicadoException(message.getMessage("service.endereco.cep.cadastrado", 
                                                                            new Object[] {enderecoCep.getLogradouro(), enderecoCep.getCidade()}, 
                                                                            LocaleContextHolder.getLocale()));
            }
        }
    }

    private void validarEnderecoCepInexistente(Usuario usuario) {
        Endereco endereco = usuario.getEndereco();
        Endereco enderecoCep = repoEndereco.findByCep(endereco.getCep());

        if (enderecoCep == null) {
            List<Endereco> enderecoLogradouro = repoEndereco.findByLogradouroAndCidade(endereco.getLogradouro(), endereco.getCidade());

            if (enderecoLogradouro != null && !enderecoLogradouro.isEmpty()) {
                throw new EnderecoLogradouroDuplicadoException(message.getMessage("service.endereco.logradouro.cadastrado", 
                                                                                    new Object[] {endereco.getLogradouro(), 
                                                                                                    endereco.getCidade(),
                                                                                                    enderecoLogradouro.get(0).getCep()}, 
                                                                                    LocaleContextHolder.getLocale()));
            }    
        }
    }

    private Endereco confirmarEndereco(Usuario usuario) {
        Endereco endereco = usuario.getEndereco();
        Endereco enderecoCep = repoEndereco.findByCep(usuario.getEndereco().getCep());

        if (enderecoCep != null) {
            if (endereco.getLogradouro().equals(enderecoCep.getLogradouro()) && endereco.getCidade().equals(enderecoCep.getCidade())
                && endereco.getNumero().equals(enderecoCep.getNumero())) {
                return enderecoCep;
            }
        }

        return endereco;    
    }
}