package com.zero.springweb.plugins.errorModule.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class ErrorResponse {
    private List<Error> errors;
}
