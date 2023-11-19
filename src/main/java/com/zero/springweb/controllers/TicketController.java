package com.zero.springweb.controllers;

import com.zero.springweb.model.Ticket;
import com.zero.springweb.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    TicketsRepository ticketsRepository;

    @Autowired
    public TicketController(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    @GetMapping
    public List<Ticket> getTickets() throws SQLException {
        return ticketsRepository.getTickets();
    }

    @PostMapping
    public void createTicket(@RequestBody Ticket ticket) throws SQLException {
        ticketsRepository.createTicket(ticket);
    }

    @PatchMapping("/{id}")
    public void updateTicket(@PathVariable int id, @RequestBody Ticket ticket) throws SQLException {
        ticket.setId(id);
        ticketsRepository.updateTicket(ticket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable int id) throws SQLException {
        ticketsRepository.deleteTicket(id);
    }
}
