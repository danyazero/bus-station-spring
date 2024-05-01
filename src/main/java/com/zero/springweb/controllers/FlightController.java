package com.zero.springweb.controllers;

import com.zero.springweb.model.Flight;
import com.zero.springweb.model.Seat;
import com.zero.springweb.model.Station;
import com.zero.springweb.repository.FlightRepository;
import com.zero.springweb.repository.TicketsRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${CORS.FRONT_URL}", allowCredentials = "true")
@RequestMapping("/api/flight")
@AllArgsConstructor
public class FlightController {

    private FlightRepository flightRepository;
    private TicketsRepository ticketsRepository;

    @GetMapping
    public List<Flight> getFlights(){
        List<Flight> flights = flightRepository.getFlights();
        System.out.println(flights.get(0).getArrivalCity() + " " + flights.get(0).getDispatchCity() + " " + flights.get(0).getBusClass());
        return flights;
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
