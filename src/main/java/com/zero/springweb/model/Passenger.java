package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Passenger {
    private int document_number;
    private String full_name;
    private Date birthday;
    private Date document_end;
    private String email;
    private Long number;
}
