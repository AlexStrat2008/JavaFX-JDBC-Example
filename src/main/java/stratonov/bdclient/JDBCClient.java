package stratonov.bdclient;

import java.sql.SQLException;

/**
 * Created by strat on 23.08.15.
 */
public interface JDBCClient {
    public boolean init(String url, String login, String password) throws SQLException;
}
