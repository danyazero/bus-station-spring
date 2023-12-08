package com.zero.springweb.controllers;

import com.zero.springweb.model.Passenger;
import com.zero.springweb.repository.PassengerRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/passenger")
@CrossOrigin("http://localhost:5173")
public class PassengerController {
    PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }

    @GetMapping
    public List<Passenger> getPassengers(){
        return passengerRepository.getPassengers();
    }
}
