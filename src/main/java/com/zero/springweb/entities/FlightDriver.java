package com.zero.springweb.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Flight_driver\"")
public class FlightDriver {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "flight_number")
    private Integer flightNumber;

    @Column(name = "driver_document")
    private Long driverDocument;

}