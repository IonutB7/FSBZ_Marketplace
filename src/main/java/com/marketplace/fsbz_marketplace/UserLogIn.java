package com.marketplace.fsbz_marketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import java.net.URL;

public class UserLogIn {

    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("chooseAccountType.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Choose Account Type");
        stage.setScene(scene);
        stage.show();
    }
    public void setRegisterButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("userRegister.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 700);
        stage.setTitle("Create new account");
        stage.setScene(scene);
        stage.show();
    }


    public void loginButtonOnAction(ActionEvent event){
        if(userTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank()==false){
            validateLogin();
        }else {
            loginMessageLabel.setText("Please enter username and password.");
        }
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + userTextField.getText() +"' AND password = '" + enterPasswordField.getText() + "'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessageLabel.setText("Congratulation!");
                }else{
                    loginMessageLabel.setText("Invalid login.Please try again.");

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
