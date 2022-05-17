package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.exceptions.EmptyFieldException;
import com.marketplace.fsbz_marketplace.exceptions.IncorectEmailException;
import com.marketplace.fsbz_marketplace.exceptions.IncorrectPasswordExeption;
import com.marketplace.fsbz_marketplace.exceptions.InsuficientLenghtException;
import com.marketplace.fsbz_marketplace.services.AdminService;
import com.marketplace.fsbz_marketplace.services.UserServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminRegisterController {
    @FXML
    private Label registerErrorMessage;
    @FXML
    private Button cancelAdminButton;
    @FXML
    private Button registerAdminButton;
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


    public void setCancelAdminButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(cancelAdminButton,"interfaces/adminLogIn.fxml",1280,720);
    }

    public void registerButtonOnAction(ActionEvent event) throws IOException {
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){

            try{
                String firstname = firstnameTextField.getText();
                String lastname = lastnameTextField.getText();
                String email = emailTextField.getText();
                String username = usernameTextField.getText();
                String  password=setPasswordField.getText();
                String saltvalue = PassBasedEnc.getSaltvalue(30);
                String encryptedPass = PassBasedEnc.generateSecurePassword(password, saltvalue);

                UserServices.verifyEmptyFilds(firstname,lastname,email,username,password);
                UserServices.verifytLenghtCredenial(firstname,lastname,username);
                UserServices.verifyEmailCorrectness(email);
                UserServices.verifyPasswordCorrectness(password);
                AdminService.registerAdmin(firstname, lastname, email, username,saltvalue,encryptedPass);
                FxmlUtilities.sceneTransiton(registerAdminButton,"interfaces/adminLogIn.fxml",1280,720);
            }catch (EmptyFieldException exception1){
                registerErrorMessage.setText(exception1.getMessage());
            }catch (InsuficientLenghtException exception2){
                registerErrorMessage.setText(exception2.getMessage());
            }catch (IncorectEmailException exception3){
                registerErrorMessage.setText(exception3.getMessage());
            } catch (IncorrectPasswordExeption exception3){
                registerErrorMessage.setText(exception3.getMessage());
            }
            catch(Exception e){
                e.printStackTrace();
                e.getCause();
                Platform.exit();
            }

        }else{
            registerErrorMessage.setText("Password does not match");
        }
    }


}
