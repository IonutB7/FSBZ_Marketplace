package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.exceptions.*;
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



public class UserRegisterController {
    @FXML
    private Button registrationButton;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label registerErrorMessage;
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
        FxmlUtilities.sceneTransiton(cancelButton,"interfaces/userLogIn.fxml",1280,720);
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
                UserServices.verifyEmailExistance("user_account",email);
                UserServices.verifyUsernameExistance("user_account",username);
                UserServices.verifyEmailCorrectness(email);
                UserServices.verifyPasswordCorrectness(password);
                UserServices.registerUser(firstname, lastname, email, username,saltvalue,encryptedPass);
                FxmlUtilities.sceneTransiton(registrationButton,"interfaces/userLogIn.fxml",1280,720);
            }catch (EmptyFieldException exception1){
                registerErrorMessage.setText(exception1.getMessage());
            }catch (InsuficientLenghtException exception2){
                registerErrorMessage.setText(exception2.getMessage());
            }catch (IncorectEmailException exception3){
            registerErrorMessage.setText(exception3.getMessage());
            } catch (IncorrectPasswordExeption exception3){
                registerErrorMessage.setText(exception3.getMessage());
            } catch(EmailExistsException exception4){
                registerErrorMessage.setText(exception4.getMessage());
            }catch(UsernameExistsException exception5){
                registerErrorMessage.setText(exception5.getMessage());
            }catch(Exception e){
                e.printStackTrace();
                e.getCause();
                Platform.exit();
            }

        }else{
            registerErrorMessage.setText("Password does not match");
        }
    }




}
