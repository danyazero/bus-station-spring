package com.zero.springweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Ticket\"")
public class Ticket {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "seat")
    private Integer seat;

    @Column(name = "bag_weight")
    private Integer bagWeight;

    @Column(name = "bag_height")
    private Integer bagHeight;

    @Column(name = "bag_width")
    private Integer bagWidth;

    @Column(name = "bag_depth")
    private Integer bagDepth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_document", referencedColumnName = "document_number")
    private Passenger passengerDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatch_city")
    private Station dispatchCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrive_city")
    private Station arriveCity;

}