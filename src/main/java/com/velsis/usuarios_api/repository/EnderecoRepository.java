package com.velsis.usuarios_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velsis.usuarios_api.entity.Endereco;


public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    public List<Endereco> findAllByCep(String cep);
    public Endereco findFirstByLogradouroAndCidadeAndUf(String logradouro, String cidade, String uf);   
}
