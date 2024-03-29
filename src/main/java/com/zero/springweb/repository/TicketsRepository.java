package com.zero.springweb.repository;

import com.zero.springweb.database.Convert;
import com.zero.springweb.database.Database;
import com.zero.springweb.model.FullTicket;
import com.zero.springweb.model.Station;
import com.zero.springweb.model.Ticket;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketsRepository {
    Database db;
    public TicketsRepository(Database db) {
        this.db = db;
    }

    public List<Ticket> getTickets() throws SQLException {
        return db.execute(Ticket.class, "SELECT T.id, T.seat, T.bag_weight, T.flight_number, T.passenger, P.full_name, P.email, SD.city AS \"dispatch_city\", SA.city AS \"arrival_city\", F.dispatch_date, F2.arrival_date, C.class AS \"bus_class\" FROM \"Ticket\" T \n" +
                "JOIN \"Passenger\" P ON T.passenger=P.document_number \n" +
                "JOIN \"Flight_Station\" F ON T.dispatch_city = F.id\n" +
                "JOIN \"Flight_Station\" F2 ON T.arrive_city = F2.id\n" +
                "JOIN \"Station\" SD ON SD.id = F.station\n" +
                "JOIN \"Station\" SA ON SA.id = F2.station\n" +
                "JOIN \"Flight\" Fl ON Fl.number = T.flight_number\n" +
                "INNER JOIN \"Bus\" B ON B.number = Fl.bus\n" +
                "INNER JOIN \"Bus_class\" C ON C.id = B.bus_class");
    }

    public FullTicket getTicket(int id){
        Ticket ticket = db.execute(Ticket.class, "SELECT T.id, T.seat, T.bag_weight, T.flight_number, T.passenger, P.full_name, P.email, SD.city AS \"dispatch_city\", SA.city AS \"arrival_city\", F.dispatch_date, F.arrival_date, C.class AS \"bus_class\" FROM \"Ticket\" T \n" +
                "INNER JOIN \"Passenger\" P ON T.passenger=P.document_number \n" +
                "INNER JOIN \"Flight\" F ON T.flight_number=F.number\n" +
                "INNER JOIN \"Station\" SD ON SD.id = F.dispatch_city\n" +
                "INNER JOIN \"Bus\" B ON B.number = F.bus\n" +
                "INNER JOIN \"Bus_class\" C ON C.id = B.bus_class\n" +
                "LEFT JOIN \"Station\" SA ON SA.id = F.arrival_city WHERE T.id = ?", id).getFirst();
        List<Station> stations = db.execute(Station.class, "SELECT S.id, C.city, S.dispatch_date, S.arrival_date FROM \"Flight_Station\" S\n" +
                "JOIN \"Station\" C ON C.id = S.station WHERE S.flight_number = ?", ticket.getFlight_number());

        return new FullTicket(ticket, stations);
    }


    public void createTicket(Ticket ticket) throws SQLException {
        db.query("INSERT INTO \"Ticket\"(bag_weight, bag_height, bag_width, bag_depth, passenger, flight_number, seat) VALUES (?, ?, ?, ?, ?, ?, ?)", ticket.getBag_weight(), 10, 35, 45, ticket.getPassenger(), ticket.getFlight_number(), ticket.getSeat());
    }

    public void deleteTicket(int id) throws SQLException {
        db.query("DELETE FROM \"Ticket\" WHERE id=?", id);
    }

    public void updateTicket(Ticket ticket) throws SQLException {
        db.query("UPDATE \"Ticket\" SET bag_weight=?, passenger=?, flight_number=?, seat=? WHERE id=?", ticket.getBag_weight(), ticket.getPassenger(), ticket.getFlight_number(), ticket.getSeat(), ticket.getId());
    }

}
