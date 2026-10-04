package com.velsis.usuarios_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UsuarioNaoEncontradoExcetion extends RuntimeException {

    public UsuarioNaoEncontradoExcetion(String message) {
        super(message);
    }
}