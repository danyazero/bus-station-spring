package com.zero.springweb.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket {
    private int id;
    private int flight_number;
    private int seat;
    private int bag_weight;
    private int passanger;
    private String fullname;
    private String email;
    private String dispatch_city;
    private String arrival_city;
    private Date dispatch_date;
    private Date arrival_date;

    public Ticket(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    public int getBag_weight() {
        return bag_weight;
    }

    public int getPassanger() {
        return passanger;
    }

    public void setPassanger(int passanger) {
        this.passanger = passanger;
    }

    public void setBag_weight(int bag_weight) {
        this.bag_weight = bag_weight;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDispatch_city() {
        return dispatch_city;
    }

    public void setDispatch_city(String dispatch_city) {
        this.dispatch_city = dispatch_city;
    }

    public String getArrival_city() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }

    public Date getDispatch_date() {
        return dispatch_date;
    }

    public void setDispatch_date(Date dispatch_date) {
        this.dispatch_date = dispatch_date;
    }

    public Date getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(Date arrival_date) {
        this.arrival_date = arrival_date;
    }
}
