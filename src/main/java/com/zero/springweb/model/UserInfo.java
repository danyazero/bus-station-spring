package com.zero.springweb.model;

import java.time.Instant;

public interface UserInfo {

    Integer getId();
    String getPhone();

    String getPassword();

    String getRole();
    Instant getPasswordChanged();
}