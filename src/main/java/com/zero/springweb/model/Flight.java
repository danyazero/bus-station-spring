package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public interface Flight {
    int getId();
    String getDispatchCity();
    String getArrivalCity();
    Date getDispatchDate();
    Date getArrivalDate();
    String getBusClass();
    int getBagWeight();
    int getBagHeight();
    int getBagWidth();
    int getBagDepth();
    Long getPurchased();
    int getFreeSeat();
    Float getPricePerKilometer();
}
