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
@Table(name = "\"Station\"")
public class Station {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "city", length = Integer.MAX_VALUE)
    private String city;

}