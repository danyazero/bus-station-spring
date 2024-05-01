package com.zero.springweb.model;

import java.util.Date;

public interface Passenger {
    int getDocumentNumber();
    String getFullName();
    Date getBirthday();
    Date getDocumentEnd();
    String getEmail();
    Long getPhone();
}
