package com.zero.springweb.repository;

import com.zero.springweb.model.Flight;
import com.zero.springweb.model.FlightWithPrice;
import com.zero.springweb.model.Station;
import com.zero.springweb.model.StationDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface FlightRepository extends CrudRepository<com.zero.springweb.entities.Flight, Integer> {

    @Query(value = """
                        SELECT
                            T.id,
                            ST2.city AS dispatchCity,
                            T.dispatch_date as dispatchDate,
                            ST.city AS arrivalCity, 
                            T.arrival_date as arrivalDate, 
                            C.class AS busClass, 
                            B.bag_height as bagHeight, 
                            B.bag_width as bagWidth,
                            B.bag_depth as bagDepth,  
                            B.bag_weight as bagWeight, 
                            (SELECT COUNT(*) FROM "Ticket" Ti WHERE Ti.flight_id = T.id) AS purchased,
                            B.seat as freeSeat,
                            B.price_per_kilometer as pricePerKilometer,
                            (ABS(SA.distance - SD.distance) * B.price_per_kilometer) as price
                        FROM "Flight" T  
                        INNER JOIN "Bus" B ON B.number = T.bus_number
                        INNER JOIN "Bus_class" C ON C.id = B.bus_class
                        JOIN "Flight_station" SA ON SA.id = T.arrival_city
                        JOIN "Flight_station" SD ON SD.id = T.dispatch_city
                        JOIN "Station" ST ON ST.id = SA.station
                        JOIN "Station" ST2 ON ST2.id = SD.station
            """, nativeQuery = true)
    List<FlightWithPrice> getFlights();

    @Query(value = """
select
    F.id,
S.city as dispatchCity,
FSS.dispatch_date as dispatchDate,
S2.city as arrivalCity,
FS.arrival_date as arrivalDate,
(SELECT COUNT(*) FROM "Ticket" Ti WHERE Ti.flight_id = F.id) AS purchased,
C.class as busClass,
B.seat as freeSeat,
B.bag_height as bagHeight,
B.bag_width as bagWidth,
B.bag_depth as bagDepth,
B.bag_weight as bagWeight,
B.price_per_kilometer as pricePerKilometer,
(ABS(FS.distance - FSS.distance) * B.price_per_kilometer) as price
from "Flight_station" FSS, "Flight" F, "Flight_station" FS, "Station" S2, "Station" S, "Bus" B, "Bus_class" C
where FSS.station = ?1 and F.id = FSS.flight_number and F.id in (
	select F.id
	from "Flight_station" FS, "Flight" F
	where FS.station = ?2 and FS.id > FSS.id and F.id = FS.flight_number
) and FS.flight_number = F.id and FS.station = ?2 and S2.id = FS.station and S.id = FSS.station and B.number = F.bus_number and C.id = B.bus_class
""", nativeQuery = true)
    List<FlightWithPrice> filterFlightsByDispatchAndArrive(int dispatchCity, int arrivalCity);

    @Query(value = """
            select * from "Station" S order by SUBSTR(S.city, 1, 1)
            """, nativeQuery = true)
    List<StationDTO> getStationsList();

    @Query(value = """
    select
        S.id,
        S.city
    from "Station" S
    where lower(S.city) like CONCAT('%',lower(?1),'%')
    or lower(S.city_ru) like CONCAT('%',lower(?1),'%')
    or lower(S.city_en) like CONCAT('%',lower(?1),'%') limit 3
""", nativeQuery = true)
    List<StationDTO> getStationByKeyword(String keyword);


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
                B.seat AS freeSeat,
                B.price_per_kilometer as pricePerKilometer
            FROM "Flight" T  
            INNER JOIN "Bus" B ON B.number = T.bus_number
            INNER JOIN "Bus_class" C ON C.id = B.bus_class
            JOIN "Flight_station" FS ON FS.id = T.arrival_city
            JOIN "Flight_station" FD ON FD.id = T.dispatch_city
            JOIN "Station" SA ON SA.id = FS.station
            JOIN "Station" SD ON SD.id = FD.station
            WHERE T.id = ?
""", nativeQuery = true)
    Flight getFlight(int id);

    @Query(value = """
    SELECT
        F.id, 
        S.city,
        F.dispatch_date as dispatchDate,
        F.arrival_date as arrivalDate,
        F.distance
    FROM "Flight_station" F
    JOIN "Station" S ON S.id = F.station WHERE F.flight_number = ?1
    ORDER BY F.distance
""", nativeQuery = true)
    List<Station> getFlightStations(int id);
}
