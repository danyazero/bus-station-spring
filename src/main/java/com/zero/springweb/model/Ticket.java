package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Ticket {
    private int id;
    private int flight_number;
    private int seat;
    private int bag_weight;
    private int passenger;
    private String full_name;
    private String bus_class;
    private String email;
    private String dispatch_city;
    private String arrival_city;
    private Date dispatch_date;
    private Date arrival_date;

    public Ticket(){}

}
