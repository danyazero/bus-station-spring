package com.zero.springweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
public class Registration implements Serializable {
    private String fullName;
    private String email;
    private long documentNumber;
    private String phone;
    private String password;
    private String passwordConfirm;
}