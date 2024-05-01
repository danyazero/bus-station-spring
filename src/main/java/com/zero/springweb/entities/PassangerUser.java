package com.zero.springweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Passanger_user\"")
public class PassangerUser {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_document", referencedColumnName = "document_number")
    private User userDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_document", referencedColumnName = "document_number")
    private Passenger passengerDocument;

}