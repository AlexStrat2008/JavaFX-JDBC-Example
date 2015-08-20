package main.java.stratonov.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.stratonov.example.bdclient.JDBCClient;

import java.io.IOException;
import java.sql.Connection;
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

    private JDBCClient jdbcClient;

    public static Connection connection = null;
    public static String login;
    private String password;

    @FXML
    private void btnLoginAction(ActionEvent event){
        login = txtUsername.getText();
        password = txtPassword.getText();
        if (validation(login, password)) {
            try {
                if(jdbcClient == null){
                    System.out.print("Все плохо");
                }
                connection = jdbcClient.createConnect("jdbc:postgresql://127.0.0.1:5432/breadsql", login, password);
                if(connection != null){
                    Parent parent = FXMLLoader.load(getClass().getResource("/sample/view/BD.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Table Frame");
                    stage.show();
                    System.out.println("Авторизация успешна прошла!");
                }
                else{
                    lblMessage.setText("login or password is not right");
                }
            } catch (SQLException e) {
                lblMessage.setText("Error : " + e.getMessage());
            } catch (IOException e) {
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
    @FXML
    public void initialize() {
        try {
            jdbcClient = new JDBCClient();
        } catch (ClassNotFoundException e) {
            System.out.print("Error : " + e.getMessage());
            jdbcClient = null;
        }
    }

}
