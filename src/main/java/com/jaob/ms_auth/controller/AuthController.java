package com.jaob.ms_auth.controller;

import com.jaob.ms_auth.aggregates.request.UsuarioSignInRequest;
import com.jaob.ms_auth.aggregates.request.UsuarioSignUpRequest;
import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.aggregates.response.SignInResponse;
import com.jaob.ms_auth.aggregates.response.UsuarioDTO;
import com.jaob.ms_auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseBase<UsuarioDTO>> registrar(@RequestBody UsuarioSignUpRequest request) {
        return new ResponseEntity<>(service.registrar(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBase<SignInResponse>> registrarUsuario(@RequestBody UsuarioSignInRequest request) {
        return new ResponseEntity<>(service.login(request), HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<ResponseBase<UsuarioDTO>> validate(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(service.validate(token), HttpStatus.OK);
    }

}
