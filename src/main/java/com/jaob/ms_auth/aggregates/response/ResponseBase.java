package com.jaob.ms_auth.aggregates.response;

import lombok.*;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseBase<T> {
    private int statusCode;
    private boolean hasError;
    private String message;
    private T data;
}
