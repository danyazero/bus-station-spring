package com.zero.springweb.controllers;

import com.zero.springweb.model.CityDTO;
import com.zero.springweb.model.ClassDTO;
import com.zero.springweb.model.CountDTO;
import com.zero.springweb.repository.BusClassRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${CORS.FRONT_URL}", allowCredentials = "true")
@RequestMapping("/api/dashboard")
@AllArgsConstructor
public class DashboardController {
    private final BusClassRepository busClassRepository;

//    @GetMapping("/flight")
//    public Object getTopFlights(@RequestParam(defaultValue = "3") int limit) {
//        //TODO: return setted count of most popular flights
//    }

    @GetMapping("/class")
    public List<ClassDTO> getTopBusClass() {
        return busClassRepository.getCountOfTicketsByBusClassId();
    }

    @GetMapping("/city")
    public List<CityDTO> getTopCities(@RequestParam(defaultValue = "3") Integer rowsLimit) {
        //TODO: return list of top cities by buyed tickets with limit of rows
        return busClassRepository.getTopCities(rowsLimit);
    }

    @GetMapping("/last")
    public List<CountDTO> getSelledTicketsLast() {
        //TODO: return count of selled tickets setted last days
        return busClassRepository.getCountTicketsPurchasedLast();
    }


}
