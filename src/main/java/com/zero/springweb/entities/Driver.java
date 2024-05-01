package com.zero.springweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"Driver\"")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "document_number", nullable = false)
    private Long documentNumber;

    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "document_end")
    private LocalDate documentEnd;

    @Column(name = "registration_addresses", length = Integer.MAX_VALUE)
    private String registrationAddresses;

    @Column(name = "phone_number", length = 12)
    private String phoneNumber;

}