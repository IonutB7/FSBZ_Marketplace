package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.services.UserServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;
import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class UserRegisterController {
    @FXML
    private Button registrationButton;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button cancelButton;

    public void setCancelButtonOnAction(ActionEvent event) throws IOException, InterruptedException {
        FxmlUtilities.sceneTransiton(cancelButton,"userLogIn.fxml",600,700);
    }

    public void registerButtonOnAction(ActionEvent event) throws IOException {
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){

            String firstname = firstnameTextField.getText();
            String lastname = lastnameTextField.getText();
            String email = emailTextField.getText();
            String username = usernameTextField.getText();
            String saltvalue = PassBasedEnc.getSaltvalue(30);
            String encryptedPass = PassBasedEnc.generateSecurePassword(setPasswordField.getText(), saltvalue);

            UserServices.registerUser(firstname, lastname, email, username, saltvalue, encryptedPass);
            FxmlUtilities.sceneTransiton(registrationButton,"userLogIn.fxml",600,700);

        }else{
            confirmPasswordLabel.setText("Password does not match");
        }
    }




}
