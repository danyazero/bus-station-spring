package com.zero.springweb.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"Passenger\"")
public class Passenger {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "document_number")
    private Long documentNumber;

    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "document_end")
    private LocalDate documentEnd;

    @Column(name = "email", length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "phone", length = 12)
    private String phone;

}