package com.jaob.ms_auth.aggregates.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioSignUpRequest {

    private String nombre;
    private String email;
    private String password;
    private String rol;

}
