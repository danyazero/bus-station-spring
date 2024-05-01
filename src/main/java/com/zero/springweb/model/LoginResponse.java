package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/*
* private String token
* */
@Getter
@Setter
@Accessors(chain = true)
public class LoginResponse {
    private String token;
}
