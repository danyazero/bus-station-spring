package com.zero.springweb.controllers;

import com.zero.springweb.model.Flight;
import com.zero.springweb.model.Seat;
import com.zero.springweb.model.Station;
import com.zero.springweb.repository.FlightRepository;
import org.springframework.web.bind.annotation.*;

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
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable int id){
        return flightRepository.getFlight(id);
    }

    @GetMapping("/{id}/stations")
    public List<Station> getFlightStations(@PathVariable int id){
        return flightRepository.getFlightStations(id);
    }

    @GetMapping("seats/{id}")
    public List<Seat> getEngagedSeats(@PathVariable int id){
        return flightRepository.getEngagedSeats(id);
    }
}
