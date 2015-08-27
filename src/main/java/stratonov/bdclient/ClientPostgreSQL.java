package stratonov.bdclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Класс-обертка над бд postgresql
 *
 * @author a.stratonov
 * @version 1.0
 */
public class ClientPostgreSQL implements JDBCClient {
    /**
     * Свойство - Объект класса
     */
    private static ClientPostgreSQL instance;
    /**
     * Свойство - Объект файла настроек
     */
    private Properties dbProperties;
    /**
     * Свойство - логин
     */
    private String login = null;
    /**
     * Свойство - пароль
     */
    private String password = null;
    /**
     * Свойство - url бд
     */
    private String dbUrl = null;
    /**
     * Свойство - схема
     */
    private String dbSchema = null;

    /**
     * Вовзращает объект класса
     *
     * @return - объект класса
     */
    public static ClientPostgreSQL getInstance() {
        return instance == null ? instance = new ClientPostgreSQL() : instance;
    }

    /**
     * Инцилизация необходимых полей из файла настроек
     */
    private ClientPostgreSQL() {
        try {
            dbProperties = getDbProperties(getClass().getResource("/properties/config.properties").openStream());
            if (dbProperties != null) {
                Class.forName(dbProperties.getProperty("db.driver"));
                dbUrl = dbProperties.getProperty("db.url");
                dbSchema = dbProperties.getProperty("db.schema");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл настроек");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает объект файла найстроек
     *
     * @param configFileInput - поток файла настроек
     * @return - объект Properties
     */
    private Properties getDbProperties(InputStream configFileInput) {
        Properties property = new Properties();
        try {
            property.load(configFileInput);
            return property;
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл настроек");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Доступ к базе дыннах по логину и паролю. Url и драйвер должен быть реализован в классе имплементирующий интерфейс.
     *
     * @param login    - логин
     * @param password - пароль
     * @return - true, если доступ разрешён
     */
    @Override
    public boolean accessToDB(String login, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, login, password);
            connection.close();
            this.login = login;
            this.password = password;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Функция для получения имени пользоватля
     *
     * @return - возвращает логин
     */
    @Override
    public String getLogin() {
        return login;
    }

    /**
     * @see JDBCClient#getTableNames()
     */
    @Override
    public List<String> getTableNames() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, login, password);
            PreparedStatement statement = connection.prepareStatement(dbProperties.getProperty("tableNamesSql"));
            statement.setString(1, dbSchema);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> arrayList = new ArrayList<>();
            while (resultSet.next()) {
                arrayList.add(resultSet.getString(1));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @see JDBCClient#getTable(String)
     */
    @Override
    public ResultSet getTable(String selectedTable) {
        Connection connection = null;
        try {
            String query = String.format(dbProperties.getProperty("getTableSql"), dbSchema, selectedTable);
            connection = DriverManager.getConnection(dbUrl, login, password);
            PreparedStatement statement = connection.prepareStatement(query.toString());
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @see JDBCClient#updateTable(String, String, String, String, String)
     */
    @Override
    public boolean updateTable(String selectedTable, String columnChangeName, String newRecord, String columnSearchName, String columnSearch) {
        Connection connection = null;
        try {
            String query = String.format(dbProperties.getProperty("updateTable"), dbSchema, selectedTable, columnChangeName, columnSearchName, columnSearch);
            connection = DriverManager.getConnection(dbUrl, login, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newRecord);
            return statement.executeUpdate() != -1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @see JDBCClient#deleteRowTable(String, String, String)
     */
    @Override
    public boolean deleteRowTable(String selectedTable, String columnSearchName, String columnSearch) {
        Connection connection = null;
        try {
            String query = String.format(dbProperties.getProperty("deleteRowTable"), dbSchema, selectedTable, columnSearchName, columnSearch);
            connection = DriverManager.getConnection(dbUrl, login, password);
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != -1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @see JDBCClient#simpleQuery(String, String)
     */
    @Override
    public boolean simpleQuery(String selectedTable, String sql) {
        Connection connection = null;
        try {
            String query = String.format(sql, dbSchema, selectedTable);
            connection = DriverManager.getConnection(dbUrl, login, password);
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != -1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
