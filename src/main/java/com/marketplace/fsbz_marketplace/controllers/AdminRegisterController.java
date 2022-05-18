package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.exceptions.*;
import com.marketplace.fsbz_marketplace.services.AdminService;
import com.marketplace.fsbz_marketplace.services.UserServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    public void registerButtonOnAction(ActionEvent event){
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){

            try{
                String firstname = firstnameTextField.getText();
                String lastname = lastnameTextField.getText();
                String email = emailTextField.getText();
                String username = usernameTextField.getText();
                String  password=setPasswordField.getText();
                String saltvalue = PassBasedEnc.getSaltvalue(30);
                String encryptedPass = PassBasedEnc.generateSecurePassword(password, saltvalue);
                String adminCode = PassBasedEnc.getSaltvalue(30).substring(0,10);

                UserServices.verifyEmptyFilds(firstname,lastname,email,username,password);
                UserServices.verifytLenghtCredenial(firstname,lastname,username);
                UserServices.verifyEmailExistance("admin_db",email);
                UserServices.verifyUsernameExistance("admin_db",username);
                UserServices.verifyEmailCorrectness(email);
                UserServices.verifyPasswordCorrectness(password);
                AdminService.registerAdmin(firstname, lastname, email, username,saltvalue,encryptedPass,adminCode);

                FxmlUtilities.sceneTransiton(registerAdminButton,"interfaces/adminLogIn.fxml",1280,720);
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("interfaces/popUps.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
                stage.setTitle("FZ:BZ Marketplace");
                PopUpsController myPOC = fxmlLoader.getController();
                myPOC.setAdminCode(adminCode);
                stage.setScene(scene);
                stage.show();

            }catch (EmptyFieldException exception1){
                registerErrorMessage.setText(exception1.getMessage());
            }catch (InsuficientLenghtException exception2){
                registerErrorMessage.setText(exception2.getMessage());
            }catch (IncorectEmailException exception3){
                registerErrorMessage.setText(exception3.getMessage());
            } catch (IncorrectPasswordExeption exception4){
                registerErrorMessage.setText(exception4.getMessage());
            } catch(EmailExistsException exception5){
                registerErrorMessage.setText(exception5.getMessage());
            }catch(UsernameExistsException exception6){
                registerErrorMessage.setText(exception6.getMessage());
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
