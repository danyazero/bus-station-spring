package com.zero.springweb.controllers;

import com.zero.springweb.model.BookTicket;
import com.zero.springweb.model.FullTicket;
import com.zero.springweb.model.Ticket;
import com.zero.springweb.repository.TicketsRepository;
import com.zero.springweb.utils.UserPrincipal;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "${CORS.FRONT_URL}", allowCredentials = "true")
@RequestMapping("/api/ticket")
public class TicketController {
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);
    TicketsRepository ticketsRepository;

    @Autowired
    public TicketController(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    @GetMapping
    public List<Ticket> getTickets(Authentication authentication) throws SQLException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return ticketsRepository.getTickets(principal.getUserId());
    }

    @GetMapping("/{flightId}")
    public FullTicket getTicket(@PathVariable int flightId){
        val ticket = ticketsRepository.getTicket(flightId);
        val stations = ticketsRepository.getFlightStations(flightId);
        return new FullTicket(ticket, stations);
    }

    @PostMapping
    public void createTicket(@RequestBody BookTicket ticket, Authentication authentication) throws SQLException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        System.out.println(ticket);
        ticketsRepository.createTicket(ticket.getBag_weight(),15, 15, 25, ticket.getPassenger(), ticket.getFlight_number(), ticket.getSeat(), ticket.getDispatch_city(), ticket.getArrival_city());
    }

    @PatchMapping("/{id}")
    public void updateTicket(@PathVariable int id, @RequestBody Ticket ticket) throws SQLException {
//        ticket.setId(id);
        ticketsRepository.updateTicket(id, ticket.getBagWeight(), ticket.getPassengerDocument(), ticket.getFlightNumber(), ticket.getSeat());
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable int id) throws SQLException {
        ticketsRepository.deleteById(id);
    }
}
