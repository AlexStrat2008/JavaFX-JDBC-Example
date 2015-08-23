package stratonov.bdclient;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by strat on 23.08.15.
 */
public abstract class JDBCClient {
    protected String login;
    protected String password;
    protected String dbDriver;
    protected String dbUrl;
    protected String dbName;

    public abstract boolean init(String dbDriver, String dbUrl, String dbName, String login, String password) throws SQLException;

    public abstract List<String> tableNames() throws SQLException;

    public String getUrl(String dbDriver, String dbUrl, String dbName){
        return new StringBuilder("jdbc:").append(dbDriver).append("://").append(dbUrl).append("/").append(dbName).toString();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbName() {
        return dbName;
    }
}
