package stratonov.bdclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by strat on 17.03.15.
 */
public class ClientPostgreSQL extends JDBCClient {
    private static ClientPostgreSQL instance;

    private ClientPostgreSQL() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        System.out.println("Драйвер postgresql подключен");
    }

    public static ClientPostgreSQL getInstance() throws ClassNotFoundException {
        return instance == null ? instance = new ClientPostgreSQL() : instance;
    }

    @Override
    public boolean init(String url, String login, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, login, password);
        super.url = url;
        super.login = login;
        super.password = password;
        return true;
    }
}
