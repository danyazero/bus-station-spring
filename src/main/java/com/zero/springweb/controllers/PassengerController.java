package com.zero.springweb.controllers;

import com.zero.springweb.model.Passenger;
import com.zero.springweb.model.PassengerDTO;
import com.zero.springweb.repository.PassengerRepository;
import com.zero.springweb.utils.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/passenger")
@CrossOrigin(origins = "${CORS.FRONT_URL}", allowCredentials = "true")
public class PassengerController {
    PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }

    @PostMapping("/new")
    public void newPassenger(@RequestBody PassengerDTO passenger, Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        System.out.println(passenger.getDocument()+ " " + passenger.getFullName()+ " " + LocalDate.now()+ " " + LocalDate.now()+ " " + passenger.getEmail()+ " " + passenger.getPhone()+ " " + principal.getUserId());
        passengerRepository.addPassenger(Long.parseLong(passenger.getDocument()), passenger.getFullName(), LocalDate.now(), LocalDate.now(), passenger.getEmail(), passenger.getPhone(), principal.getUserId());
    }

    @GetMapping
    public List<Passenger> getPassengers(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return passengerRepository.getPassengerBy(principal.getUserId());
    }
}
