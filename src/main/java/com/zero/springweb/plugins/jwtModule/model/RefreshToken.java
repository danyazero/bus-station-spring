package com.zero.springweb.plugins.jwtModule.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RefreshToken {
    private String refreshToken;
    private  String sessionToken;
}