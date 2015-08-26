package stratonov.bdclient;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by strat on 23.08.15.
 */
public interface JDBCClient {
    boolean accessToDB(String login, String password);

    String getLogin();

    List<String> getTableNames();

    ResultSet getTable(String selectedTable);

    boolean updateTable(String selectedTable, String columnChangeName, String newRecord, String columnSearchName, String columnSearch);
}
