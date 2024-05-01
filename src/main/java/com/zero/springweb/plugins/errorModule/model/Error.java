package com.zero.springweb.plugins.errorModule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {
    private Code code;
    private String message;
}
