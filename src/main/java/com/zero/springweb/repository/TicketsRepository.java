package com.zero.springweb.repository;

import com.zero.springweb.database.Convert;
import com.zero.springweb.database.Database;
import com.zero.springweb.model.Ticket;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketsRepository {
    Database db;
    Convert convert = new Convert();

    public TicketsRepository(Database db) {
        this.db = db;
    }

    public List<Ticket> getTickets() throws SQLException {
        return db.execute("SELECT T.id, T.seat, T.bag_weight, T.flight_number, T.passanger, P.fullname, P.email, SD.city AS \"dispatch_city\", SA.city AS \"arrival_city\", F.dispatch_date, F.arrival_date FROM \"Ticket\" T \n" +
                "INNER JOIN \"Passanger\" P ON T.passanger=P.document_number \n" +
                "INNER JOIN \"Flight\" F ON T.flight_number=F.number\n" +
                "INNER JOIN \"Station\" SD ON SD.id = F.dispatch_city\n" +
                "LEFT JOIN \"Station\" SA ON SA.id = F.arrival_city");
//        return convert.processTicketSet(resultSet);
    }

    public void createTicket(Ticket ticket) throws SQLException {
        db.query("INSERT INTO \"Ticket\"(bag_weight, bag_height, bag_width, bag_depth, passanger, flight_number, seat) VALUES (?, ?, ?, ?, ?, ?, ?)", ticket.getBag_weight(), 10, 35, 45, ticket.getPassanger(), ticket.getFlight_number(), ticket.getSeat());
    }

    public void deleteTicket(int id) throws SQLException {
        db.query("DELETE FROM \"Ticket\" WHERE id=?", id);
    }

    public void updateTicket(Ticket ticket) throws SQLException {
        db.query("UPDATE \"Ticket\" SET bag_weight=?, passanger=?, flight_number=?, seat=? WHERE id=?", ticket.getBag_weight(), ticket.getPassanger(), ticket.getFlight_number(), ticket.getSeat(), ticket.getId());
    }

}
