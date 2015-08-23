package stratonov.bdclient;

import java.sql.SQLException;

/**
 * Created by strat on 23.08.15.
 */
public abstract class JDBCClient {
    protected String url = null;
    protected String login = null;
    protected String password = null;

    public abstract boolean init(String url, String login, String password) throws SQLException;

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
