package com.zero.springweb.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"Flight_station\"")
public class FlightStation {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "flight_number")
    private Integer flightNumber;

    @Column(name = "station")
    private Integer station;

    @Column(name = "dispatch_date")
    private Instant dispatchDate;

    @Column(name = "arrival_date")
    private Instant arrivalDate;

}