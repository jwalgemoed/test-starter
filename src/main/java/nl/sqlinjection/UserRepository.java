package nl.sqlinjection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static nl.sqlinjection.DatabaseConstants.*;

public class UserRepository {
    
    public List<String> findUsers(final String name) throws Exception {
        final List<String> users = new ArrayList<>();
        final String query = "SELECT * FROM users WHERE name = '" + name + "'; ";
        try (
                final Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                final Statement statement = connection.createStatement();
                final ResultSet resultSet = statement.executeQuery(query);
        ) {
            while(resultSet.next()) {
                users.add(resultSet.getString(2));
            }
        }
        return users;
    }
    
    public void insertUser(final String name) throws Exception {
        final String query = "INSERT INTO users (name) VALUES ('" + name + "');";
        try (final Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             final Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }
}
