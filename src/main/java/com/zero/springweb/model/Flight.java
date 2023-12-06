package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Flight {
    private int id;
    private String dispatch_city;
    private String arrival_city;
    private Date dispatch_date;
    private Date arrival_date;
    private String bus_class;
    private int bag_weight;
    private int bag_height;
    private int bag_width;
    private int bag_depth;
    private Long purchased;
    private int free_seat;
}
