package com.zero.springweb.repository;

import com.zero.springweb.database.Database;
import com.zero.springweb.model.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public class PassengerRepository {
    Database db;
    public PassengerRepository(Database db) {
        this.db = db;
    }

    public List<Passenger> getPassengers(){
        return db.execute(Passenger.class, "SELECT * FROM \"Passenger\"");
    }
}
