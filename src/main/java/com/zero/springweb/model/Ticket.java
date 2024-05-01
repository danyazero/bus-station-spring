package com.zero.springweb.model;

import java.util.Date;

public interface Ticket {
    int getId();
    int getFlightNumber();
    int getSeat();
    int getBagWeight();
    int getPassengerDocument();
    String getFullName();
    String getBusClass();
    String getEmail();
    String getDispatchCity();
    String getArrivalCity();
    Date getDispatchDate();
    Date getArrivalDate();
}
