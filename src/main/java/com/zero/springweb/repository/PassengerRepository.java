package com.zero.springweb.repository;

import com.zero.springweb.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PassengerRepository extends CrudRepository<com.zero.springweb.entities.Passenger, Integer> {
    @Query(value = """
                        select
            P.phone,
            P.document_number as documentNumber,
            P.full_name as fullName,
            P.birthday,
            P.document_end as documentEnd,
            P.email
            from "User" U
            join "Passenger" P on P.user_id = U.id
            WHERE U.id = ?1
            """, nativeQuery = true)
    List<Passenger> getPassengerBy(int userId);

    @Modifying
    @Transactional
    @Query(value = """
insert into "Passenger" (document_number, full_name, birthday, document_end, email, phone, user_id) values (?1, ?2, ?3, ?4, ?5, ?6, ?7)
""", nativeQuery = true)
    Integer addPassenger(Long documentNumber, String fullName, LocalDate birthday, LocalDate documentEnd, String email, String phone, Integer userId);
}
