package com.zero.springweb.controllers;

import com.zero.springweb.model.*;
import com.zero.springweb.repository.FlightRepository;
import com.zero.springweb.repository.TicketsRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
@CrossOrigin(origins = "${CORS.FRONT_URL}", allowCredentials = "true")
@RequestMapping("/api/flight")
@AllArgsConstructor
public class FlightController {

    private FlightRepository flightRepository;
    private TicketsRepository ticketsRepository;

    @GetMapping
    public List<FlightWithPrice> getFlights(){

        return flightRepository.getFlights();
    }

    @GetMapping("/filter")
    public List<FlightWithPrice> getFlightsByFilter(
            @RequestParam int dispatchCity,
            @RequestParam int arrivalCity
    ) {

        System.out.println(dispatchCity + " " + arrivalCity);


        return flightRepository.filterFlightsByDispatchAndArrive(dispatchCity, arrivalCity);
    }

    @GetMapping("/station")
    public List<StationDTO> getStationsList() {
        return flightRepository.getStationsList();
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
        return ticketsRepository.getEngagedSeats(id);
    }
}
