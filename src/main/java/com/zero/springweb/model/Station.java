package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Station {
    private int id;
    private String city;
    private Date dispatch_date;
    private Date arrival_date;
}
