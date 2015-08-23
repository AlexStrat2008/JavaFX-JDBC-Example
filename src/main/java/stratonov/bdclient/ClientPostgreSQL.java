package stratonov.bdclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by strat on 17.03.15.
 */
public class ClientPostgreSQL extends JDBCClient {
    private static ClientPostgreSQL instance;
    private String url;
    private static final String tableNamesSql = "select table_name from information_schema.tables WHERE table_schema = ?";

    private ClientPostgreSQL() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        System.out.println("Драйвер postgresql подключен");
    }

    public static ClientPostgreSQL getInstance() throws ClassNotFoundException {
        return instance == null ? instance = new ClientPostgreSQL() : instance;
    }

    @Override
    public boolean init(String dbDriver, String dbUrl, String dbName, String login, String password){
        this.url = super.getUrl(dbDriver,dbUrl,dbName);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, login, password);
            connection.close();
        } catch (SQLException e) {
            return false;
        }
        super.dbDriver = dbDriver;
        super.dbUrl = dbUrl;
        super.dbName = dbName;
        super.login = login;
        super.password = password;
        return true;
    }

    @Override
    public List<String> tableNames() throws SQLException {
        Connection connection = DriverManager.getConnection(url, super.login, super.password);
        PreparedStatement statement = connection.prepareStatement(tableNamesSql);
        return null;
    }
}
