package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.exceptions.AccountNotActivatedException;
import com.marketplace.fsbz_marketplace.exceptions.BannedUserException;
import com.marketplace.fsbz_marketplace.exceptions.InexistentUserException;
import com.marketplace.fsbz_marketplace.exceptions.UserPasswordInvalidException;
import com.marketplace.fsbz_marketplace.services.AdminService;
import com.marketplace.fsbz_marketplace.services.UserServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminLogInController {
    @FXML
    private Button registerAdminButton;
    @FXML
    private Button loginAdminButton;
    @FXML
    private Button cancelAdminButton;

    @FXML
    private TextField adminTextField;
    @FXML
    private PasswordField adminPasswordField;
    @FXML
    private TextField adminCodeField;
    @FXML
    private Label loginMessageLabel;


    public void setAdminCancelButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(cancelAdminButton,"interfaces/chooseAccountType.fxml",1280,720);
    }
    public void setAdminRegisterButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(registerAdminButton,"interfaces/adminRegister.fxml",1280,720);
    }

    public void adminLoginButtonOnAction(ActionEvent event){
        if(adminTextField.getText().isBlank() ==false && adminPasswordField.getText().isBlank()==false&&adminCodeField.getText().isBlank()==false){

            try{
                if(AdminService.validateLogin(adminTextField.getText(), adminPasswordField.getText(),adminCodeField.getText())==true){

                    FxmlUtilities.sceneTransiton(loginAdminButton,"interfaces/adminMainInterface.fxml",1280,720);
                }
            }catch(InexistentUserException exception1){
                loginMessageLabel.setText(exception1.getMessage());
            }catch (AccountNotActivatedException exception2){
                loginMessageLabel.setText(exception2.getMessage());
            }catch(UserPasswordInvalidException exception3){
                loginMessageLabel.setText(exception3.getMessage());
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
                Platform.exit();
            }

        }else {
            loginMessageLabel.setText("Please enter username,password and the admin code.");
        }
    }

}
