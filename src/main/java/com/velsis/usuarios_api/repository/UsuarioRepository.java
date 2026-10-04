package com.velsis.usuarios_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velsis.usuarios_api.entity.Usuario;
import java.util.List;
import com.velsis.usuarios_api.entity.Endereco;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
    
    public Usuario findByCpf(String cpf);

    public List<Usuario> findAllByEndereco(Endereco endereco);
}