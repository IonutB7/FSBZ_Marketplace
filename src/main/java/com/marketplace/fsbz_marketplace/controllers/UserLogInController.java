package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.BannedUserException;
import com.marketplace.fsbz_marketplace.exceptions.InexistentUserException;
import com.marketplace.fsbz_marketplace.exceptions.UserPasswordInvalidException;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;
import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import com.marketplace.fsbz_marketplace.services.UserServices;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class UserLogInController {

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
        Stage stage = (Stage)cancelButton.getScene().getWindow();
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
        if(userTextField.getText().isBlank() ==false && enterPasswordField.getText().isBlank()==false){

            try{
                UserServices.validateLogin(userTextField.getText(), enterPasswordField.getText(),loginButton);
            }catch(InexistentUserException exception1){
                loginMessageLabel.setText(exception1.getMessage());
            }catch (BannedUserException exception2){
                loginMessageLabel.setText(exception2.getMessage());
            }catch(UserPasswordInvalidException exception3){
                loginMessageLabel.setText(exception3.getMessage());
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

        }else {
            loginMessageLabel.setText("Please enter username and password.");
        }
    }


}
