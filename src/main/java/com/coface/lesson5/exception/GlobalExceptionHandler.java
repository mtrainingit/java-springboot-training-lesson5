package com.coface.lesson5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    ResponseEntity<?> recursoNoEncontradoException(RecursoNoEncontradoException exception, WebRequest request) {
        DetalleDeError detalleDeError = new DetalleDeError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(detalleDeError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictoCampoUnicoException.class)
    ResponseEntity<?> conflictoCampoUnicoException(ConflictoCampoUnicoException exception, WebRequest request) {
        DetalleDeError detalleDeError = new DetalleDeError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(detalleDeError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CampoOrdenDesconocido.class)
    ResponseEntity<?> campoOrdenDesconocidoException(CampoOrdenDesconocido exception, WebRequest request) {
        DetalleDeError detalleDeError = new DetalleDeError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(detalleDeError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    ResponseEntity<?> insufficientAuthenticationException(InsufficientAuthenticationException exception, WebRequest request) {
        DetalleDeError detalleDeError = new DetalleDeError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(detalleDeError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
        DetalleDeError detalleDeError = new DetalleDeError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(detalleDeError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<?> badCredentialsException(BadCredentialsException exception, WebRequest request) {
        DetalleDeError detalleDeError = new DetalleDeError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(detalleDeError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> cualquierException(Exception exception, WebRequest request) {
        DetalleDeError detalleDeError = new DetalleDeError(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(detalleDeError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
