package com.jaob.ms_auth.controller;

import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.exceptions.FormatoIncorrectoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.InputMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseBase<String>> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ResponseBase<String> response = new ResponseBase<>(
                Constantes.CODE_NOT_FOUND,
                true,
                exception.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity<ResponseBase<String>> handleInputMismatchException(InputMismatchException exception) {
        ResponseBase<String> response = new ResponseBase<>(
                Constantes.CODE_BAD_REQUEST,
                true,
                exception.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ResponseBase<String>> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        ResponseBase<String> response = new ResponseBase<>(
                Constantes.CODE_BAD_REQUEST,
                true,
                "Falta el header: " + exception.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FormatoIncorrectoException.class)
    public ResponseEntity<ResponseBase<String>> handleFormatoIncorrectoException(FormatoIncorrectoException exception) {
        ResponseBase<String> response = new ResponseBase<>(
                Constantes.CODE_BAD_REQUEST,
                true,
                exception.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
