package stratonov.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by strat on 24.03.15.
 */
public class DialogAddController implements Initializable {
    public AnchorPane anchorPaneDialogAdd;
    public Button idBottomAdd;
    private TableView tableView;
    private ObservableList<String> newElem;


    public void onActionBottomAdd(ActionEvent actionEvent) {
        ObservableList<javafx.scene.Node> list = anchorPaneDialogAdd.getChildren();
        try {
//            INSERT INTO bread.client (lvl_trust, company) VALUES (2,'Cheese');
//            Statement statement = LoginController.connection.createStatement();
            String sqlAdd = " INSERT INTO bread." + BDController.itemAction.getText() + " (";
            for (int i = 1; i < tableView.getColumns().size(); i++) {
                if (i+1 == tableView.getColumns().size()) {
                    sqlAdd += ((TableColumn) tableView.getColumns().get(i)).getText();
                    continue;
                }
                sqlAdd += ((TableColumn) tableView.getColumns().get(i)).getText() + ", ";
            }
            sqlAdd += ") VALUES (";

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof TextField) {
                    if (i+1 == list.size()) {
                        sqlAdd += "'" + ((TextField) list.get(i)).getText() + "');";
                        continue;
                    }
                    sqlAdd += "'" + ((TextField) list.get(i)).getText() + "',";
                }
            }
//            statement.executeUpdate(sqlAdd);
            BDController.updateDate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        System.out.print("Данные добавлены!");
        //SELECT data_type FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'client';
        Stage stage = (Stage) idBottomAdd.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (BDController.tableViewGet != null) {
            tableView = BDController.tableViewGet;
            for (int i = 1; i < tableView.getColumns().size(); i++) {
                Label labl = new Label(((TableColumn) tableView.getColumns().get(i)).getText());
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
}
