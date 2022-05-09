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
import javafx.scene.paint.Paint;
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

        String retriveEncryptedPassStatement ="SELECT encryptedPass,salt,banned FROM user_account WHERE username = '" + userTextField.getText() +"'";

        try{
           Statement statement = connectionDB.createStatement();
           ResultSet queryResult = statement.executeQuery(retriveEncryptedPassStatement);

            while(queryResult.next()){
                String proviedPassword = enterPasswordField.getText();
                String retrivedEncryptedPass = queryResult.getString("encryptedPass");
                String retrivedSalt = queryResult.getString("salt");
                int isBanned = queryResult.getInt("banned");

                if(PassBasedEnc.verifyUserPassword(proviedPassword,retrivedEncryptedPass,retrivedSalt)==true&&isBanned==0){

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("marketplaceInterface.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 700);
                    stage.setTitle("FS:BZ Marketplace");
                    stage.setScene(scene);
                    stage.show();

                }else if(isBanned==1){
                    loginMessageLabel.setTextFill(Paint.valueOf("#FF0000"));
                    loginMessageLabel.setText("The user is banned.");
                }else{
                    loginMessageLabel.setTextFill(Paint.valueOf("#FF0000"));
                    loginMessageLabel.setText("Invalid username or password.Please try again.");

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
