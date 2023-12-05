package com.zero.springweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FullTicket {
    private Ticket ticket;
    private List<Station> stations;
}
