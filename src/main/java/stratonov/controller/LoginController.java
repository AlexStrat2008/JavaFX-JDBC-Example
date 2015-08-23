package stratonov.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import stratonov.bdclient.ClientPostgreSQL;

import java.sql.SQLException;

/**
 * Created by strat on 17.03.15.
 */
public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMessage;

    @FXML
    private void btnLoginAction(ActionEvent event){
        String login = txtUsername.getText();
        String password = txtPassword.getText();
        if (validation(login, password)) {
            try {
                ClientPostgreSQL jdbcClient = ClientPostgreSQL.getInstance();
                jdbcClient.init("jdbc:postgresql://127.0.0.1:5432/Example", login, password);
//                Parent parent = FXMLLoader.load(getClass().getResource("view/BD.fxml"));
//                Stage stage = new Stage();
//                Scene scene = new Scene(parent);
//                stage.setScene(scene);
//                stage.setTitle("Table Frame");
//                stage.show();
                System.out.println("Авторизация успешна прошла!");
            } catch (SQLException e) {
                lblMessage.setText("Error : " + e.getMessage());
//            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            lblMessage.setText("login or password is not validation");
        }
    }

    private boolean validation(String login, String password) {
        if (txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
            lblMessage.setText("Username or Password invalid!");
            return false;
        } else
        return true;
    }
}
