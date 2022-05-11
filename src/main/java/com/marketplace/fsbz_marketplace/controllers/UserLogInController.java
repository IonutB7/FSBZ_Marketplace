package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.BannedUserException;
import com.marketplace.fsbz_marketplace.exceptions.InexistentUserException;
import com.marketplace.fsbz_marketplace.exceptions.UserPasswordInvalidException;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import com.marketplace.fsbz_marketplace.services.UserServices;

import java.io.IOException;
import java.sql.Connection;


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


    @FXML
    private void setUserInstance(ActionEvent event,String username) {
        User currentUser = new User();
        UserServices.initializeUser(currentUser,username);
        InventoryServices.initializeUserInventory(currentUser, currentUser.getInventoryId());
        UserHolder holder = UserHolder.getInstance();
        holder.setUser(currentUser);
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("chooseAccountType.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Account Login");
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
                if(UserServices.validateLogin(userTextField.getText(), enterPasswordField.getText(),loginButton)==true){

                    setUserInstance(event,userTextField.getText());

                    Stage stage = (Stage) cancelButton.getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("marketplaceInterface.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setTitle("FZ:BZ Marketplace");
                    stage.setScene(scene);
                    stage.show();
                }
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
