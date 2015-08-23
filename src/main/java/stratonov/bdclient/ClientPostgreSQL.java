package stratonov.bdclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by strat on 17.03.15.
 */
public class ClientPostgreSQL implements JDBCClient {
    private String url;
    private String login;
    private String password;
    private static ClientPostgreSQL instance;

    private ClientPostgreSQL() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        System.out.println("Драйвер подключен");
        login = "postgres";
        password = "postgres";
        url = "jdbc:postgresql://127.0.0.1:5432/Example";
    }

    public static ClientPostgreSQL getInstance() throws ClassNotFoundException {
        return instance == null ? instance = new ClientPostgreSQL() : instance;
    }

    @Override
    public boolean init(String url, String login, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, login, password);
        this.url = url;
        this.login = login;
        this.password = password;
        return true;
    }
}
