package com.zero.springweb.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    private final String phone;
    private final String password;
    private final Boolean remember;
}

