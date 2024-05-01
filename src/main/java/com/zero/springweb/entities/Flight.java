package com.zero.springweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"Flight\"")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dispatch_date")
    private Instant dispatchDate;

    @Column(name = "arrival_date")
    private Instant arrivalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_number", referencedColumnName = "number")
    private Bus busNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatch_city")
    private Station dispatchCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_city")
    private Station arrivalCity;

}