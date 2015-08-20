package main.java.stratonov.example.bdclient;

import java.sql.*;

/**
 * Created by strat on 17.03.15.
 */
public class JDBCClient {
    public JDBCClient() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    public Connection createConnect(String url, String name, String password) throws SQLException {
        if (url.equals("")) throw new SQLException("url не задано");
        return DriverManager.getConnection(url, name, password);
    }

    public static ResultSet doQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        return result;
    }
}
