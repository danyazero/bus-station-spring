package com.zero.springweb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public interface Station {
    int getId();
    String getCity();
    Date getDispatchDate();
    Date getArrivalDate();
}
