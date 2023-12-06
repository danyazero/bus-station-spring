package com.zero.springweb.database;

import com.zero.springweb.model.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
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


    public <T> List<T> execute(Class<T> clazz, String SQL, Object... values) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL);
            prepareStatement(statement, values);
            ResultSet resultSet = statement.executeQuery();
            Convert convert = new Convert();

            List<T> items = new ArrayList<>();
            while(resultSet.next()) {
                T item = clazz.getDeclaredConstructor().newInstance();
                convert.fillObjectFromResultSet(item, resultSet);
                items.add(item);
            }

            return items;
        } catch (SQLException throwables){
            throwables.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void query(String SQL, Object... values) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        prepareStatement(preparedStatement, values);

        preparedStatement.execute();
    }

    private void prepareStatement(PreparedStatement preparedStatement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            switch (values[i]) {
                case Integer num -> preparedStatement.setInt(i+1, num);
                case String s -> preparedStatement.setString(i+1, s);
                case Date date -> preparedStatement.setDate(i+1, date);
                case Long lon -> preparedStatement.setLong(i+1, lon);
                case null, default -> {
                    assert values[i] != null;
                    throw new SQLException("Невідомий тип: " + values[i].getClass().getName());
                }
            }
        }
    }
}
