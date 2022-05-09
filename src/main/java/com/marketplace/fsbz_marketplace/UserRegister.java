package com.marketplace.fsbz_marketplace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;


public class UserRegister {
    @FXML
    private Button registrationButton;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label registrationMessageLabel;
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

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("userLogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("User log-in");
        stage.setScene(scene);
        stage.show();
    }

    public void registerButtonOnAction(ActionEvent event){
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
        }else{
            confirmPasswordLabel.setText("Password does not match");
        }
    }



    public void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String saltvalue = PassBasedEnc.getSaltvalue(30);
        String encryptedPass = PassBasedEnc.generateSecurePassword(setPasswordField.getText(), saltvalue);
        int banned = 1;

        String insertFields = "INSERT INTO user_account(firstname,lastname,email,username,encryptedPass,salt,banned) VALUES ('";
        String insertValues = firstname+"','" +lastname+"','" +email+"','" +username+"','" +encryptedPass+"','"+saltvalue+"','"+banned+"')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            registrationMessageLabel.setText("User has been registered succesfully!");


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }

}
