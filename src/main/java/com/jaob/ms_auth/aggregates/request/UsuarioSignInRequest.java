package com.jaob.ms_auth.aggregates.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioSignInRequest {

    private String email;
    private String password;
}
