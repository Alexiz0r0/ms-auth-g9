package com.jaob.ms_auth.controller;

import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.aggregates.response.UsuarioDTO;
import com.jaob.ms_auth.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/user")
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<ResponseBase<List<UsuarioDTO>>> listarSuperAdmins() {
        return new ResponseEntity<>(service.listarUsuarios(), HttpStatus.OK);
    }
}
