package com.jaob.ms_auth.service;

import com.jaob.ms_auth.aggregates.request.UsuarioSignInRequest;
import com.jaob.ms_auth.aggregates.request.UsuarioSignUpRequest;
import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.aggregates.response.SignInResponse;
import com.jaob.ms_auth.aggregates.response.UsuarioDTO;
import com.jaob.ms_auth.entity.Usuario;

public interface AuthenticationService {

    ResponseBase<UsuarioDTO> registrar(UsuarioSignUpRequest request);

    ResponseBase<SignInResponse> login(UsuarioSignInRequest request);

    ResponseBase<UsuarioDTO> validate(String token);
}
