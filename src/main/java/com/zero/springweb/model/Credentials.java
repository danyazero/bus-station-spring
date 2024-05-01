package com.zero.springweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Credentials {
    private Integer userId;
    private String phone;
    private List<String> roles;
}