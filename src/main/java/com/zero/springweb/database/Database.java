package com.zero.springweb.database;

import com.zero.springweb.model.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class Database {
    private static Connection connection;

    public Database(
            @Value("${database.url}") String URL,
            @Value("${database.username}") String USERNAME,
            @Value("${database.password}") String PASSWORD
    ) {

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public List<Ticket> execute(String SQL){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            Convert convert = new Convert();

            List<Ticket> tickets = new ArrayList<>();
            while(resultSet.next()) {
                Ticket ticket = new Ticket();
                convert.fillObjectFromResultSet(ticket, resultSet);

                tickets.add(ticket);
            }

            return tickets;
        } catch (SQLException throwables){
            throwables.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void query(String SQL, Object... values) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        for (int i = 0; i < values.length; i++) {
            switch (values[i]) {
                case Integer num -> preparedStatement.setInt(i+1, num);
                case String s -> preparedStatement.setString(i+1, s);
                case Date date -> preparedStatement.setDate(i+1, date);
                case null, default -> {
                    assert values[i] != null;
                    throw new SQLException("Невідомий тип: " + values[i].getClass().getName());
                }
            }
        }

        preparedStatement.execute();
    }
}
