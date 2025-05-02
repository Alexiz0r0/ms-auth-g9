package com.jaob.ms_auth.aggregates.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignInResponse {
    private String nombre;
    private String email;
    private String token;
}
