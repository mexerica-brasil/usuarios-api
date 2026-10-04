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
import com.velsis.usuarios_api.exception.UsuarioCpfDataNascimentoNaoAlteravelException;
import com.velsis.usuarios_api.exception.UsuarioCpfDuplicadoException;
import com.velsis.usuarios_api.exception.UsuarioIdNaoInformadoException;
import com.velsis.usuarios_api.exception.UsuarioNaoEncontradoExcetion;
import com.velsis.usuarios_api.repository.EnderecoRepository;
import com.velsis.usuarios_api.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service 
public class UsuarioService {

    private static final Integer MINIMO_USUARIO_MESMO_ENDERECO = 1; 

    private static final Integer ID_INVALIDO = 0;

    @Autowired 
    private MessageSource message;

    @Autowired 
    private UsuarioRepository repository;

    @Autowired 
    private EnderecoRepository repoEndereco;

    public List<Usuario> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    @Transactional 
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

    private void validarEndereco(Usuario usuario) {
        if (usuario.getEndereco() != null) {
            this.validarEnderecoCepExistente(usuario);
            this.validarEnderecoCepInexistente(usuario);
        }
    }

    private void validarEnderecoCepExistente(Usuario usuario) {
        Endereco endereco = usuario.getEndereco();

        List<Endereco> enderecosCep = repoEndereco.findAllByCep(usuario.getEndereco().getCep());

        if (enderecosCep != null && !enderecosCep.isEmpty()) {
            for (Endereco enderecoCep : enderecosCep) {
                if (!endereco.getLogradouro().equals(enderecoCep.getLogradouro()) || !endereco.getCidade().equals(enderecoCep.getCidade())
                    || !endereco.getUf().equals(enderecoCep.getUf())) {
                    throw new EnderecoCepDuplicadoException(message.getMessage("service.endereco.cep.cadastrado", 
                                                                                new Object[] {enderecoCep.getLogradouro(), enderecoCep.getCidade()}, 
                                                                                LocaleContextHolder.getLocale()));
                }
            }
        }
    }

    private void validarEnderecoCepInexistente(Usuario usuario) {
        Endereco endereco = usuario.getEndereco();
        List<Endereco> enderecosCep = repoEndereco.findAllByCep(endereco.getCep());

        if (enderecosCep != null && enderecosCep.isEmpty() || enderecosCep == null) {
            Endereco enderecoLogradouro = repoEndereco.findFirstByLogradouroAndCidadeAndUf(endereco.getLogradouro(), endereco.getCidade(), endereco.getUf());

            if (enderecoLogradouro != null) {
                throw new EnderecoLogradouroDuplicadoException(message.getMessage("service.endereco.logradouro.cadastrado", 
                                                                                    new Object[] {endereco.getLogradouro(), 
                                                                                                    endereco.getCidade(),
                                                                                                    endereco.getUf(),
                                                                                                    enderecoLogradouro.getCep()}, 
                                                                                    LocaleContextHolder.getLocale()));
            }    
        }
    }

    private Endereco confirmarEndereco(Usuario usuario) {
        if (usuario.getEndereco() == null) {
            return null;
        }

        Endereco endereco = usuario.getEndereco();
        endereco.setId(null);
        Endereco enderecoParaSalvar = endereco;
        
        List<Endereco> enderecosCep = repoEndereco.findAllByCep(usuario.getEndereco().getCep());

        if (enderecosCep != null && !enderecosCep.isEmpty()) {
            for (Endereco enderecoCep : enderecosCep) {
               if (endereco.getLogradouro().equals(enderecoCep.getLogradouro()) && endereco.getCidade().equals(enderecoCep.getCidade())
                    && endereco.getUf().equals(enderecoCep.getUf())
                    && endereco.getNumero().equals(enderecoCep.getNumero()) ) {
                    enderecoParaSalvar = enderecoCep;
                    break;
                } 
            }
        }

        return enderecoParaSalvar;   
    }

    @Transactional
    public void exluir(Integer id) {
        Usuario usuario = this.recuperarUsuarioPeloId(id);
        
        List<Usuario> usuarios = repository.findAllByEndereco(usuario.getEndereco());

        if (usuarios != null && usuarios.size() > MINIMO_USUARIO_MESMO_ENDERECO) {
            usuario.setEndereco(null);
        }                                                       

        repository.delete(usuario);
    }

    private Usuario recuperarUsuarioPeloId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoExcetion(
                                                                message.getMessage("service.usuario.nao.encontrado",
                                                                                    new Object[] {id}, 
                                                                                    LocaleContextHolder.getLocale())));
    }

    @Transactional 
    public Usuario alterar(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().equals(ID_INVALIDO)) {
            throw new UsuarioIdNaoInformadoException(message.getMessage("service.usuario.id.nao.informado", 
                                                                            new Object[] {usuario.getNome()},
                                                                            LocaleContextHolder.getLocale()));
        }
        
        Usuario usuarioSalvo = this.recuperarUsuarioPeloId(usuario.getId());

        if (!usuario.getCpf().equals(usuarioSalvo.getCpf()) || !usuario.getDataNascimento().equals(usuarioSalvo.getDataNascimento())) {
            throw new UsuarioCpfDataNascimentoNaoAlteravelException(message.getMessage("service.usuario.cpf.dataNascimento.nao.alteravel", 
                                                                                            new Object[] {usuario.getNome()}, 
                                                                                            LocaleContextHolder.getLocale()));
        }

        Integer idEnderecoUsuarioSalvo = usuarioSalvo.getEndereco() != null ? usuarioSalvo.getEndereco().getId() : null;

        this.validarUsuario(usuario);
        usuario.setEndereco(this.confirmarEndereco(usuario));

        usuario.setDataCriacao(usuarioSalvo.getDataCriacao());

        Usuario usuarioNovo = repository.save(usuario);

        if (idEnderecoUsuarioSalvo != null && !idEnderecoUsuarioSalvo.equals(usuarioNovo.getEndereco().getId())) {
            List<Usuario> usuariosEnderecoId = repository.findAllByEnderecoId(idEnderecoUsuarioSalvo);

            if (usuariosEnderecoId != null && usuariosEnderecoId.size() < MINIMO_USUARIO_MESMO_ENDERECO || usuariosEnderecoId == null) { 
                repoEndereco.deleteById(idEnderecoUsuarioSalvo);
            }
        }

        return usuarioNovo;
    }
}