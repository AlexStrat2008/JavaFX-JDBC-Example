package stratonov.bdclient;

import java.sql.ResultSet;
import java.util.List;

/**
 * Интерфейс для работы с базой данных
 *
 * @author a.stratonov
 * @version 1.0
 */
public interface JDBCClient {
    /**
     * Доступ к базе дыннах по логину и паролю. Url и драйвер должен быть реализован в классе имплементирующий интерфейс.
     *
     * @param login    - логин
     * @param password - пароль
     * @return - true, если доступ разрешён
     */
    boolean accessToDB(String login, String password);

    /**
     * Функция для получения имени пользоватля
     *
     * @return - возвращает логин
     */
    String getLogin();

    /**
     * Функция для вовзрата всех таблиц, доступных пользователю
     *
     * @return - имя
     */
    List<String> getTableNames();

    /**
     * Возвращает все данные таблицы
     *
     * @param selectedTable - имя таблицы
     * @return - возврат данных с бд
     */
    ResultSet getTable(String selectedTable);

    /**
     * Функция для update таблицы
     *
     * @param selectedTable    - имя таблицы
     * @param columnChangeName - имя изменяемой колонки
     * @param newRecord        - новое значение
     * @param columnSearchName - имя колонки по которой ведется поиск (id)
     * @param columnSearch     - выбранная колонка(id)
     * @return - true, если update успешный
     */
    boolean updateTable(String selectedTable, String columnChangeName, String newRecord, String columnSearchName, String columnSearch);

    /**
     * Функция удаления данных из таблицы
     *
     * @param selectedTable    - имя таблицы
     * @param columnSearchName - имя колонки по которой ведется поиск (id)
     * @param columnSearch     - выбранная колонка(id)
     * @return - true, если удаление успешно
     */
    boolean deleteRowTable(String selectedTable, String columnSearchName, String columnSearch);

    /**
     * Простой запрос к бд
     *
     * @param selectedTable - имя таблицы
     * @param sql           - запрос к бд
     * @return - true, если запрос выполнен успешно
     */
    boolean simpleQuery(String selectedTable, String sql);
}
