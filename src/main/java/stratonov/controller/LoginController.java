package stratonov.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stratonov.bdclient.ClientPostgreSQL;
import stratonov.bdclient.JDBCClient;

import java.io.IOException;

/**
 * Created by strat on 17.03.15.
 */
public class LoginController {
    private static final String dbDriver = "postgresql";
    private static final String dbUrl = "127.0.0.1:5432";
    private static final String dbName = "Example";

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private void btnLoginAction(ActionEvent event) {
        String login = txtUsername.getText();
        String password = txtPassword.getText();
        try {
            if (login.isEmpty() || password.isEmpty()) {
                throw new Exception("Укажите логин или пароль!");
            }
            JDBCClient jdbcClient = ClientPostgreSQL.getInstance();
            if (jdbcClient.init(dbDriver, dbUrl, dbName, login, password)) {
                Parent parent = FXMLLoader.load(getClass().getResource("/view/BD.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Table Frame");
                stage.setScene(new Scene(parent));
                stage.show();
                System.out.println("Авторизация успешна прошла!");
            }
            else{
                new Alert(Alert.AlertType.ERROR, "Подключение не произошло.\nПроверьте логин или пароль.").showAndWait();
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, "Не найдена view BD.fxml").showAndWait();
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
