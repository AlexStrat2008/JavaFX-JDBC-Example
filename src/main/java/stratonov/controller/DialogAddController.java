package stratonov.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import stratonov.bdclient.ClientPostgreSQL;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by strat on 24.03.15.
 */
public class DialogAddController implements Initializable {
    public AnchorPane anchorPaneDialogAdd;
    public Button idBottomAdd;
    private List<String> columnNames;
    private String selectedTable;

    public DialogAddController(List<String> columnNames, String selectedTable) {
        this.columnNames = columnNames;
        this.selectedTable = selectedTable;
    }

    public void onActionBottomAdd(ActionEvent actionEvent) {
        ObservableList<Node> list = anchorPaneDialogAdd.getChildren();
        String sqlAdd = " INSERT INTO %s.%s (";
        for (int i = 1; i < columnNames.size(); i++) {
            if (i + 1 == columnNames.size()) {
                sqlAdd += columnNames.get(i);
                continue;
            }
            sqlAdd += columnNames.get(i) + ", ";
        }
        sqlAdd += ") VALUES (";

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) instanceof TextField) {
                if (i + 1 == list.size()) {
                    sqlAdd += "'" + ((TextField) list.get(i)).getText() + "');";
                    continue;
                }
                sqlAdd += "'" + ((TextField) list.get(i)).getText() + "',";
            }
        }
        ClientPostgreSQL.getInstance().simpleQuery(selectedTable, sqlAdd);
        showTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (columnNames != null) {
            for (int i = 1; i < columnNames.size(); i++) {
                Label labl = new Label(columnNames.get(i));
                labl.setId("labl" + i);
                labl.setLayoutX(50);
                labl.setLayoutY(40 + i * 35);

                TextField textField = new TextField();
                textField.setId("textFild" + i);
                textField.setLayoutX(200);
                textField.setLayoutY(40 + i * 35);
                anchorPaneDialogAdd.getChildren().add(labl);
                anchorPaneDialogAdd.getChildren().add(textField);
            }
        }
    }

    private void showTable() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BD.fxml"));
            BDController dialogAddController = new BDController(selectedTable);
            loader.setController(dialogAddController);
            Stage stage = new Stage();
            stage.setTitle("Таблица");
            stage.setScene(new Scene(loader.load()));
            stage.show();
            ((Stage) idBottomAdd.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
