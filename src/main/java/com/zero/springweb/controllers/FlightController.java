package com.zero.springweb.controllers;

import com.zero.springweb.model.Flight;
import com.zero.springweb.repository.FlightRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/flight")
public class FlightController {

    private FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }

    @GetMapping
    public List<Flight> getFlights(){
        return flightRepository.getFlights();
//        Flight flight = new Flight();
//        flight.
    }
}
