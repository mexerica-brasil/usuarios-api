package com.velsis.usuarios_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velsis.usuarios_api.entity.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
    
}
