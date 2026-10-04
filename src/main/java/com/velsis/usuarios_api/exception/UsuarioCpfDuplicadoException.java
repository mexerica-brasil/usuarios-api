package com.velsis.usuarios_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsuarioCpfDuplicadoException extends  RuntimeException {

    public UsuarioCpfDuplicadoException(String message) {
        super(message);
    }
}