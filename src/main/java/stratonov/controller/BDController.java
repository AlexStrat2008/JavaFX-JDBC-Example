package stratonov.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import stratonov.bdclient.ClientPostgreSQL;
import stratonov.bdclient.JDBCClient;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by strat on 25.03.15.
 */
public class BDController implements Initializable {
    private String dbSchema = "bread";
    public SplitMenuButton smbTableBase;

    public TableView tableView;
    public static TableView tableViewGet;
    private static Statement statement = null;
    public static MenuItem itemAction;
    private static ObservableList<ObservableList> data;
    public Button outBottom;
    public TextField testSearch;
    public Label lblLogin;
    public SplitMenuButton columTableView;
    private ObservableList editDate;
    private TableColumn idTableColumn1;
    private JDBCClient jdbcClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jdbcClient = ClientPostgreSQL.getInstance();
        lblLogin.setText(jdbcClient.getLogin());
        List<String> nameTablesSet = jdbcClient.getTableNames();
        if (nameTablesSet != null) {
            MenuItem menuItem;
            for (String iter : nameTablesSet) {
                menuItem = new MenuItem(iter);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        cliningTable();
                        fillingTable(((MenuItem) event.getSource()).getText());
                    }
                });
                smbTableBase.getItems().addAll(menuItem);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Таблицы не найдены!").showAndWait();
        }
//        try {
////            statement = LoginController.connection.createStatement();
////          select table_name from information_schema.tables WHERE table_schema = 'bread'
//            ResultSet result1 = statement.executeQuery("select table_name from information_schema.tables WHERE table_schema = 'bread'");
//            while (result1.next()) {
//                final MenuItem elem = new MenuItem(result1.getString(1));
//                elem.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {

//                        tableView.getColumns().clear();
//                        columTableView.getItems().clear();
//                        itemAction = (MenuItem) event.getSource();
////                            Select * From bread.tablel
////                        if (LoginController.login.equals("worker") && itemAction.getText().equals("order")) {
////                            outBottom.setVisible(true);
////                        }
//                        data = FXCollections.observableArrayList();
//                        try {
//                            ResultSet rs = statement.executeQuery("Select * From bread." + itemAction.getText());
//                            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                                final int j = i;
//                                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
//                                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
//                                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                                    }
//                                });
//                                col.setCellFactory(TextFieldTableCell.forTableColumn());
//                                col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
//                                    @Override
//                                        TableColumn tableColumn = event.getTableColumn();
//                                    public void handle(TableColumn.CellEditEvent event) {
//                                        TablePosition tablePosition = event.getTablePosition();
//                                        try {
//                                            statement.executeUpdate("UPDATE bread." + itemAction.getText() + " SET " + tableColumn.getText() + " = " + event.getNewValue() + " WHERE " + idTableColumn1.getText() + " = " + data.get(tablePosition.getRow()).get(0) + ";");
//                                        } catch (SQLException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                                tableView.getColumns().addAll(col);
//                            }
//                            while (rs.next()) {
//                                ObservableList<String> row = FXCollections.observableArrayList();
//                                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                                    row.add(rs.getString(i));
//                                }
//                                data.add(row);
//                            }
//                            tableView.setItems(data);
//                            MenuItem item = new MenuItem("all");
//                            item.setOnAction(new EventHandler<ActionEvent>() {
//                                @Override
//                                public void handle(ActionEvent event) {
//                                    tableView.setItems(data);
//                                }
//                            });
//                            columTableView.getItems().add(item);
//                            for (int i = 0; i < tableView.getColumns().size(); i++) {
//                                MenuItem itme = new MenuItem(((TableColumn) tableView.getColumns().get(i)).getText());
//                                itme.setOnAction(new EventHandler<ActionEvent>() {
//                                    @Override
//                                    public void handle(ActionEvent event) {
//                                        try {
//                                            if(!testSearch.getText().equals("")) {
//                                                ResultSet rs1 = statement.executeQuery("Select * From bread." + itemAction.getText() + " where " + ((MenuItem) event.getSource()).getText() + " = '" + testSearch.getText() + "';");
//                                                ObservableList<ObservableList> dataSearch = FXCollections.observableArrayList();
//                                                while (rs1.next()) {
//
//                                                    ObservableList<String> row = FXCollections.observableArrayList();
//                                                    for (int i = 1; i <= rs1.getMetaData().getColumnCount(); i++) {
//                                                        row.add(rs1.getString(i));
//                                                    }
//
//                                                    dataSearch.add(row);
//                                                }
//                                                tableView.setItems(dataSearch);
//                                            }
//                                        } catch (SQLException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                                columTableView.getItems().add(itme);
//                            }
//                            editDate = tableView.getSelectionModel().getSelectedItems();
//                            idTableColumn1 = ((TableColumn) tableView.getColumns().get(0));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//                smbTableBase.getItems().addAll(elem);
//                tableViewGet = tableView;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private void cliningTable() {
        tableView.getColumns().clear();
        tableView.getItems().clear();
    }

    private void fillingTable(String selectedTable) {
        ResultSet resultSet = jdbcClient.getTable(selectedTable);
        try {
            if (resultSet != null) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                fillingColumnsTable(resultSetMetaData, selectedTable);
                ObservableList<List<String>> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    List<String> row = new ArrayList<>();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row.add(resultSet.getString(i));
                    }
                    data.add(row);
                }
                tableView.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillingColumnsTable(ResultSetMetaData resultSetMetaData, String selectedTable) throws SQLException {
        for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i) {
            TableColumn column = new TableColumn(resultSetMetaData.getColumnName(i + 1));
            final int finalI = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
                    return new ReadOnlyStringWrapper(data.getValue().get(finalI));
                }
            });
            column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                public void handle(TableColumn.CellEditEvent event) {
                    TablePosition tablePosition = event.getTablePosition();
                    String columnSearch = ((List) tableView.getItems().get(tablePosition.getRow())).get(0).toString();
                    String columnSearchName = ((TableColumn) tableView.getColumns().get(0)).getText();
                    if(!jdbcClient.updateTable(selectedTable, event.getTableColumn().getText(), event.getNewValue().toString(), columnSearchName, columnSearch)){
                        new Alert(Alert.AlertType.WARNING, "Данные не изменены.").showAndWait();
                    }
                }
            });
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            tableView.getColumns().add(column);
        }
    }

    public void onActionDelete(ActionEvent actionEvent) {
//        if (editDate != null) {
//            try {
//                statement.executeUpdate("DELETE FROM bread." + itemAction.getText() + " WHERE " + idTableColumn1.getText() + " = " + ((ObservableList) editDate.get(0)).get(0) + ";");
//                data.removeAll(editDate);
//                tableView.setItems(data);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            editDate = null;
//        } else
//            System.out.println("данные не введены!");
    }

    public void onActionAdd(ActionEvent actionEvent) {
//        Parent parent = null;
//        try {
//            parent = FXMLLoader.load(getClass().getResource("stratonov/view/DialogAdd.fxml"));
//            Stage stage = new Stage();
//            Scene scene = new Scene(parent);
//            stage.setScene(scene);
//            stage.setTitle("DialogAdd");
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void updateDate() {
//        data.clear();
//        ResultSet rs = statement.executeQuery("Select * From bread." + itemAction.getText());
//        while (rs.next()) {
//            ObservableList<String> row = FXCollections.observableArrayList();
//            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                row.add(rs.getString(i));
//            }
//            data.add(row);
//        }
    }

    public void outBottomAction(ActionEvent actionEvent) {
//        File file = new File("/home/strat/out.doc");
//        try {
//            FileWriter writer = new FileWriter(file);
//            writer.append("Кассовый чек!\n");
//            ObservableList observableList = data.get(data.size() - 1);
//            System.out.println("select family_name, 'name', surname from bread.worker where id_worker = " + observableList.get(3) + ";");
//            System.out.println("select 'company' from bread.client where id_client = " + observableList.get(5) + ";");
//            ResultSet rs1 = statement.executeQuery("select family_name, name, surname from bread.worker where id_worker = " + observableList.get(3) + ";");
//            rs1.next();
//            writer.append("Время заказа: " + observableList.get(2) + "\n");
//            writer.append("Продавец:" + rs1.getString(1) + " " + rs1.getString(2) + " " + rs1.getString(3) + "\n");
//            rs1.close();
//            ResultSet rs2 = statement.executeQuery("select company from bread.client where id_client = " + observableList.get(5) + ";");
//            rs2.next();
//            writer.append("Покупатель: " + rs2.getString(1) + "\n");
//            rs2.close();
//            writer.append("Количество: " + observableList.get(1) + "\n");
//            ResultSet rs3 = statement.executeQuery("select price, name_goods from bread.goods where id_goods = " + observableList.get(4) + ";");
//            rs3.next();
//            writer.append("Продукт: " + rs3.getString(2) + "\n");
//            writer.append("Цена за единицу: " + rs3.getString(1) + "\n");
//            writer.append("Общая стоимость: " + (Integer.parseInt(rs3.getString(1)) * Integer.parseInt((String) observableList.get(1))) + "\n");
//            writer.flush();
//            System.out.println("Отчет записан в файл");
//        } catch (IOException e) {
//            System.out.println("Ошибка записи в файл");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }
}
