package com.zero.springweb.plugins.errorModule;


import com.zero.springweb.model.HttpStatusCodes;
import com.zero.springweb.plugins.errorModule.model.Code;
import com.zero.springweb.plugins.errorModule.model.CommonException;
import com.zero.springweb.plugins.errorModule.model.Error;
import com.zero.springweb.plugins.errorModule.model.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
@RequestScope
@ControllerAdvice
public class ErrorModule {
    public ErrorBuilder builder() {
        return new ErrorBuilder();
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException ex) {
        System.out.println("Common: " + ex.getErrors().size());
        return new ResponseEntity<>(ErrorResponse.builder().errors(ex.getErrors()).build(), HttpStatusCode.valueOf(ex.getHttpStatus()));
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public void handleValidationExceptions(MethodArgumentNotValidException ex) {
//        this.builder().validateArguments(ex.getBindingResult()).throwErrorIfPresent(HttpStatusCodes.NOT_ACCEPTABLE.getValue());
//    }
    public class ErrorBuilder {
        private final Set<Error> errorList = new HashSet<>();

        public ErrorBuilder validatePasswords(String firstPassword, String secondPassword){
            if (!firstPassword.equals(secondPassword)) {
                errorList.add(new Error(Code.REQUEST_VALIDATION_ERROR, "Паролі не співпадають"));
            }
            return this;
        }

        public ErrorBuilder addError(String error, Code code) {
            if (error != null) errorList.add(new Error(code, error));
            return this;
        }

        public ErrorBuilder addError(Boolean data, String error, Code code) {
            if (data) errorList.add(new Error(code, error));
            return this;
        }

        public ErrorBuilder checkNull(Object data, String error, Code code) {
            if (data == null) errorList.add(new Error(code, error));
            return this;
        }

        public ErrorBuilder checkRegex(String value, Pattern pattern, String message) {
            var matcher = pattern.matcher(value).find();
            System.out.println(matcher);
            if (!matcher) errorList.add(new Error(Code.REQUEST_VALIDATION_ERROR, message));

            return this;
        }

        public ErrorBuilder checkSize(String value, int min, int max, String message) {
            if (value.length() < min || value.length() > max) {
                this.addError(message, Code.REQUEST_VALIDATION_ERROR);
            }

            return this;
        }
        public List<Error> getErrorList() {
            return errorList.stream().toList();
        }

        public void throwErrorIfPresent(int httpStatus) {
            if (hasErrors()) throw CommonException.builder().errors(getErrorList()).httpStatus(httpStatus).build();
        }

        public Boolean hasErrors(){
            return !errorList.isEmpty();
        }

    }
}
