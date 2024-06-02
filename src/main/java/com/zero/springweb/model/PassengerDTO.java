package com.zero.springweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PassengerDTO {
    private String document;
    private String fullName;
    private String email;
    private String phone;
}
