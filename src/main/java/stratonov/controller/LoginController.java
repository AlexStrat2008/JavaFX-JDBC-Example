package stratonov.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stratonov.bdclient.ClientPostgreSQL;
import stratonov.bdclient.JDBCClient;

import java.io.IOException;

/**
 * Контроллер окна авторизации
 *
 * @author a.stratonov
 * @version 1.0
 */
public class LoginController {
    /**
     * Свойство - кнопка авторизация
     */
    public Button btnLogin;
    /**
     * Свойство - поле для ввода логина
     */
    @FXML
    private TextField txtUsername;
    /**
     * Свойство - поле для ввода пароля
     */
    @FXML
    private PasswordField txtPassword;

    /**
     * Проврка данных пользователя, в случае успешной авторизации переходит на окно с таблицами.
     *
     * @param event
     */
    @FXML
    private void btnLoginAction(ActionEvent event) {
        String login = txtUsername.getText();
        String password = txtPassword.getText();
        try {
            if (login.isEmpty() || password.isEmpty()) {
                throw new Exception("Укажите логин или пароль!");
            }
            JDBCClient jdbcClient = ClientPostgreSQL.getInstance();
            if (jdbcClient.accessToDB(login, password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BD.fxml"));
                BDController dialogAddController = new BDController();
                loader.setController(dialogAddController);
                Stage stage = new Stage();
                stage.setTitle("Таблица");
                stage.setScene(new Scene(loader.load()));
                stage.show();
                ((Stage) btnLogin.getScene().getWindow()).close();
                System.out.println("Авторизация успешна прошла!");
            } else {
                new Alert(Alert.AlertType.ERROR, "Подключение не произошло.\nПроверьте логин или пароль.").showAndWait();
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, "Не найдена view BD.fxml").showAndWait();
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
