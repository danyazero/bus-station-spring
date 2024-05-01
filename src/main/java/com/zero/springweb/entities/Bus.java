package com.zero.springweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Bus\"")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "seat")
    private Integer seat;

    @Column(name = "bag_height")
    private Integer bagHeight;

    @Column(name = "bag_weight")
    private Integer bagWeight;

    @Column(name = "bus_class")
    private Integer busClass;

    @Column(name = "bag_depth")
    private Integer bagDepth;

    @Column(name = "bag_width")
    private Integer bagWidth;

}