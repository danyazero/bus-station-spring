package com.zero.springweb.repository;

import com.zero.springweb.database.Database;
import com.zero.springweb.model.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public class FlightRepository {
    Database db;
    public FlightRepository(Database db) {
        this.db = db;
    }

    @GetMapping
    public List<Flight> getFlights(){
        return db.execute(Flight.class, "SELECT T.number AS \"id\", SD.city AS \"dispatch_city\", T.dispatch_date, SA.city AS \"arrival_city\", T.arrival_date, C.class AS \"bus_class\", \n" +
                "B.bag_height, B.bag_width, B.bag_depth,  B.bag_weight, (SELECT COUNT(*) FROM \"Ticket\" Ti WHERE Ti.flight_number = T.number) AS \"purchased\", B.seat AS free_seat FROM \"Flight\" T  \n" +
                "INNER JOIN \"Bus\" B ON B.number = T.bus\n" +
                "INNER JOIN \"Bus_class\" C ON C.id = B.bus_class\n" +
                "JOIN \"Station\" SA ON SA.id = T.arrival_city\n" +
                "JOIN \"Station\" SD ON SD.id = T.dispatch_city");
    }
}

//SELECT T.number, T.dispatch_date, T.arrival_date, C.class AS "bus_class", B.bag_height, B.bag_width, B.bag_depth,  B.bag_weight FROM "Flight" T
//INNER JOIN "Bus" B ON B.number = T.bus
//INNER JOIN "Bus_class" C ON C.id = B.bus_class