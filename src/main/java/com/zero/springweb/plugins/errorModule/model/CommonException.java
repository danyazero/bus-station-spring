package com.zero.springweb.plugins.errorModule.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * class CommonException extends RuntimeException
 *      @value errors [{code, message}]
 *      @value httpStatus
 */

@Builder
@Setter
@Getter
public class CommonException extends RuntimeException {
    private final List<Error> errors;
    private final Integer httpStatus;

}