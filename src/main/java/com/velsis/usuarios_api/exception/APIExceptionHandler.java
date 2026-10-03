package com.velsis.usuarios_api.exception;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice 
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Autowired 
    private MessageSource messageSource;

    private List<Problem> criarListaErros(BindingResult bindingResult, int status, String title, Exception ex) {
        List<Problem> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msgUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String msgDev = fieldError.toString();
            erros.add(new Problem(status, title, ex.getMessage() ,msgUsuario, msgDev));
        }        

        return erros;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

        String msgUsuario = messageSource.getMessage("exception.conteudo.invalido", null, LocaleContextHolder.getLocale());
		
		Problem problem = new Problem(status.value(), problemType.getTitle(), ex.getCause().toString(), msgUsuario, msgUsuario);

		return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        List<Problem> erros = criarListaErros(ex.getBindingResult(), status.value(), problemType.getTitle(), ex);
        
        return handleExceptionInternal(ex, erros, headers, status, request);
    }

}