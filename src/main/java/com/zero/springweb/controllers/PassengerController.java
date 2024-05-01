package com.zero.springweb.controllers;

import com.zero.springweb.model.Passenger;
import com.zero.springweb.repository.PassengerRepository;
import com.zero.springweb.utils.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/passenger")
@CrossOrigin(origins = "${CORS.FRONT_URL}", allowCredentials = "true")
public class PassengerController {
    PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }

    @GetMapping
    public List<Passenger> getPassengers(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return passengerRepository.getPassengerBy(principal.getUserId());
    }
}
