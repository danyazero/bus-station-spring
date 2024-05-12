package com.zero.springweb.repository;

import com.zero.springweb.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//select
//            T.seat,
//            P.fullName
//            from "Ticket" T
//            join "Passenger" P on P.documentNumber = T.passengerDocument.documentNumber
//            where T.flight.id = ?1

public interface TicketsRepository extends CrudRepository<com.zero.springweb.entities.Ticket, Integer> {
    @Query(value = """
            select
                T.seat,
                P.full_name as fullName
            from "Ticket" T
            join "Passenger" P on P.document_number = T.passenger_document
            where T.flight_id = ?1
            """, nativeQuery = true)
    List<Seat> getEngagedSeats(int flight_number);

    @Modifying
    @Query(value = """
            UPDATE "Ticket" SET bag_weight=?2, passenger_document=?3, flight_id=?4, seat=?5 WHERE id=?1
""", nativeQuery = true)
    void updateTicket(int id, int bag_weight, int passenger, int flight_number, int seat);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO "Ticket" (bag_weight, bag_height, bag_width, bag_depth, passenger_document, flight_id, seat, dispatch_city, arrive_city, calculated_price) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)
""", nativeQuery = true)
    void createTicket(int bag_weight, int bag_height, int bag_width, int bag_depth, long passenger, int flight_number, int seat, int dispatch_city, int arrive_city, double calculated_price);

    @Query(value = """
with first_distance as (
	select
	FS.distance
	from "Flight_station" FS
	WHERE FS.id = ?1
)
select
(ABS(FD.distance - FS.distance) * B.price_per_kilometer) as price
from "Flight_station" FS, first_distance FD, "Flight" F, "Bus" B
WHERE F.id = FS.flight_number and FS.id = ?2 and B.number = F.bus_number;
""", nativeQuery = true)
    PriceDTO getCalculatedPrice(int dispatch_station, int arrive_station);

    @Query(value = """
                        SELECT
                            T.id,
                            T.seat,
                            T.bag_weight as bagWeight,
                            T.flight_id as flightNumber,
                            T.passenger_document as passengerDocument,
                            T.calculated_price as calculatedPrice,
                            P.full_name as fullName,
                            P.email,
                            SD.city AS dispatchCity,
                            SA.city AS arrivalCity,
                            F.dispatch_date as dispatchDate,
                            F2.arrival_date as arrivalDate,
                            C.class AS busClass
                        FROM "User" U
                        JOIN "Passanger_user" PU ON PU.user_document = U.document_number
                        JOIN "Ticket" T ON T.passenger_document = PU.passenger_document
                        JOIN "Passenger" P ON T.passenger_document=P.document_number
                        JOIN "Flight_station" F ON T.dispatch_city = F.id
                        JOIN "Flight_station" F2 ON T.arrive_city = F2.id
                        JOIN "Station" SD ON SD.id = F.station
                        JOIN "Station" SA ON SA.id = F2.station
                        JOIN "Flight" Fl ON Fl.id = T.flight_id
                        INNER JOIN "Bus" B ON B.number = Fl.bus_number
                        INNER JOIN "Bus_class" C ON C.id = B.bus_class
                        WHERE U.id = ?1
                        ORDER BY T.id DESC
            """, nativeQuery = true)
    List<Ticket> getTickets(int userId);

    @Query(value = """

            SELECT 
                T.id, 
                T.seat, 
                T.bag_weight as bagWeight, 
                T.flight_id as flightNumber,
                T.passenger_document,
                P.full_name as fullName,
                P.email,
                SD.city AS dispatchCity,
                SA.city AS arrivalCity,
                F.dispatch_date as dispatchDate,
                F.arrival_date as arrivalDate,
                C.class AS busClass 
            FROM "Ticket" T 
            INNER JOIN "Passenger" P ON T.passenger_document=P.document_number 
            INNER JOIN "Flight" F ON T.flight_id=F.id
            INNER JOIN "Station" SD ON SD.id = F.dispatch_city
            INNER JOIN "Bus" B ON B.number = F.bus_number
            INNER JOIN "Bus_class" C ON C.id = B.bus_class
            LEFT JOIN "Station" SA ON SA.id = F.arrival_city WHERE T.id = ?1
""", nativeQuery = true)
    Ticket getTicket(int id);
    
    @Query(value = """
            SELECT
                S.id, 
                C.city, 
                S.dispatch_date as dispatchDate,
                S.arrival_date as arrivalDate,
                S.distance
            FROM "Flight_station" S
            JOIN "Station" C ON C.id = S.station WHERE S.flight_number = ?1
""", nativeQuery = true)
    List<Station> getFlightStations(int flight_number);
}
