package com.zero.springweb.repository;

import com.zero.springweb.model.Flight;
import com.zero.springweb.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<com.zero.springweb.entities.Flight, Integer> {

    @Query(value = """
                        SELECT
                            T.id,
                            SD.city AS dispatchCity,
                            T.dispatch_date as dispatchDate,
                            SA.city AS arrivalCity, 
                            T.arrival_date as arrivalDate, 
                            C.class AS busClass, 
                            B.bag_height as bagHeight, 
                            B.bag_width as bagWidth,
                            B.bag_depth as bagDepth,  
                            B.bag_weight as bagWeight, 
                            (SELECT COUNT(*) FROM "Ticket" Ti WHERE Ti.flight_id = T.id) AS purchased, 
                            B.seat as freeSeat
                        FROM "Flight" T  
                        INNER JOIN "Bus" B ON B.number = T.bus_number
                        INNER JOIN "Bus_class" C ON C.id = B.bus_class
                        JOIN "Station" SA ON SA.id = T.arrival_city
                        JOIN "Station" SD ON SD.id = T.dispatch_city
            """, nativeQuery = true)
    List<Flight> getFlights();

    @Query(value = """

            SELECT 
                T.id AS id, 
                SD.city AS dispatchCity, 
                T.dispatch_date as dispatchDate, 
                SA.city AS arrivalCity, 
                T.arrival_date as arrivalDate,
                C.class AS busClass, 
                B.bag_height as bagHeight, 
                B.bag_width as bagWidth,
                B.bag_depth as bagDepth,  
                B.bag_weight as bagWeight, 
                (SELECT COUNT(*) FROM "Ticket" Ti WHERE Ti.flight_id = T.id) AS purchased, 
                B.seat AS freeSeat 
            FROM "Flight" T  
            INNER JOIN "Bus" B ON B.number = T.bus_number
            INNER JOIN "Bus_class" C ON C.id = B.bus_class
            JOIN "Station" SA ON SA.id = T.arrival_city
            JOIN "Station" SD ON SD.id = T.dispatch_city WHERE T.id = ?
""", nativeQuery = true)
    Flight getFlight(int id);

    @Query(value = """
    SELECT
        F.id, 
        S.city,
        F.dispatch_date as dispatchDate,
        F.arrival_date as arrivalDate
    FROM "Flight_station" F
    JOIN "Station" S ON S.id = F.station WHERE F.flight_number = ?1
""", nativeQuery = true)
    List<Station> getFlightStations(int id);
}
