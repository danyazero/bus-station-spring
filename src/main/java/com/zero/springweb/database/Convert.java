package com.zero.springweb.database;

import com.zero.springweb.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Convert {
    public List<Ticket> processTicketSet(ResultSet resultSet) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        while(resultSet.next()) {
            Ticket ticket = new Ticket();

            ticket.setId(resultSet.getInt("id"));
            ticket.setSeat(resultSet.getInt("seat"));
            ticket.setFlight_number(resultSet.getInt("flight_number"));
            ticket.setPassanger(resultSet.getInt("passanger"));
            ticket.setFullname(resultSet.getString("fullname"));
            ticket.setEmail(resultSet.getString("email"));
            ticket.setBag_weight(resultSet.getInt("bag_weight"));
            ticket.setDispatch_city(resultSet.getString("dispatch_city"));
            ticket.setArrival_city(resultSet.getString("arrival_city"));
            ticket.setDispatch_date(resultSet.getTimestamp("dispatch_date"));
            ticket.setArrival_date(resultSet.getTimestamp("arrival_date"));

            tickets.add(ticket);
        }

        return tickets;
    }
}
