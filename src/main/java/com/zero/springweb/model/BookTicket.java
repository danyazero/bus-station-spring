package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookTicket {
    private int flight_number;
    private int seat;
    private int bag_weight;
    private int dispatch_city;
    private int arrival_city;
    private long passenger;
}
