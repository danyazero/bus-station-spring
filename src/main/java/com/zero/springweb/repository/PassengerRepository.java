package com.zero.springweb.repository;

import com.zero.springweb.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassengerRepository extends JpaRepository<com.zero.springweb.entities.Passenger, Integer> {
    @Query(value = """
                        select
            P.phone,
            P.document_number as documentNumber,
            P.full_name as fullName,
            P.birthday,
            P.document_end as documentEnd,
            P.email
            from "User" U
            join "Passanger_user" PU on PU.user_document = U.document_number
            join "Passenger" P on P.document_number = PU.passenger_document
            WHERE U.id = ?1
            """, nativeQuery = true)
    List<Passenger> getPassengerBy(int userId);
}
