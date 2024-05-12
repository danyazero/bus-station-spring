package com.zero.springweb.repository;

import com.zero.springweb.entities.BusClass;
import com.zero.springweb.model.CityDTO;
import com.zero.springweb.model.ClassDTO;
import com.zero.springweb.model.CountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusClassRepository extends JpaRepository<BusClass, Integer> {
    @Query(value = """
select
    BC.class as className, 
    COUNT(B.id) as count
from "Ticket" T, "Bus" B, "Flight" F, "Bus_class" BC
WHERE F.id = T.flight_id AND B.number = F.bus_number AND BC.id = B.bus_class
GROUP BY B.bus_class, BC.class
""", nativeQuery = true)
    List<ClassDTO> getCountOfTicketsByBusClassId();

    @Query(value = """

            select
            COUNT(T.id) as count,
            S.city as cityName
            from "Ticket" T, "Station" S
            where S.id in (T.arrive_city, T.dispatch_city)
            group by S.city order by count DESC limit ?1
            """, nativeQuery = true)
    List<CityDTO> getTopCities(int rowsLimit);

    @Query(value = """
            select
                '7 days' as title,
                COUNT(T.id) as count,
                SUM(T.calculated_price) as sum
            from "Ticket" T
            where T.purchased >= NOW() - INTERVAL '7 days'
            UNION
            select
                '14 days' as title,
                COUNT(T.id) as count,
                SUM(T.calculated_price) as sum
            from "Ticket" T
            where T.purchased >= NOW() - INTERVAL '14 days'
            UNION
            select
                '30 days' as title,
                COUNT(T.id) as count,
                SUM(T.calculated_price) as sum
            from "Ticket" T
            where T.purchased >= NOW() - INTERVAL '30 days'
            order by count
            """, nativeQuery = true)
    List<CountDTO> getCountTicketsPurchasedLast();
}