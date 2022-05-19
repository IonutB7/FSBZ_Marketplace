package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.*;
import com.marketplace.fsbz_marketplace.model.*;
import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.services.LedgerService;
import com.marketplace.fsbz_marketplace.services.WalletServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import com.marketplace.fsbz_marketplace.services.UserServices;

import java.io.IOException;
import java.util.ArrayList;


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
    private void setUserInstance(String username) {
        User currentUser = new User();
        UserServices.initializeUser(currentUser,username);
        InventoryServices.initializeUserInventory(currentUser, currentUser.getInventoryId());
        LedgerService.initializeUserLedger(currentUser,currentUser.getInventoryId());
        UserHolder holder = UserHolder.getInstance();
        holder.setUser(currentUser);
    }

    private void setStoreInvetoryInstance() {
        ArrayList<Item> storeInventory = new ArrayList<>();
        InventoryServices.initializeStoreInventory(storeInventory);
        StoreInventoryHolder holder = StoreInventoryHolder.getInstance();
        holder.setStoreInventory(storeInventory);
    }

    private void setStoreCouponList(){
        ArrayList<Coupon> storeCouponList = new ArrayList<>();
        WalletServices.loadStoreCoupons(storeCouponList);
        StoreCouponHolder holder = StoreCouponHolder.getInstance();
        holder.setStoreCouponList(storeCouponList);
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {

        FxmlUtilities.sceneTransiton(cancelButton,"interfaces/chooseAccountType.fxml",1280,720);
    }
    public void setRegisterButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(registerButton,"interfaces/userRegister.fxml",1280,720);
    }


    public void loginButtonOnAction(ActionEvent event){
        if(userTextField.getText().isBlank() ==false && enterPasswordField.getText().isBlank()==false){

            try {
                try{
                    if(UserServices.validateLogin(userTextField.getText(), enterPasswordField.getText())==true){

                        if(UserServices.verifyIfWarned(userTextField.getText())==true){
                           FxmlUtilities.setSanctionPopUp(userTextField.getText());
                        }

                        setUserInstance(userTextField.getText());
                        setStoreInvetoryInstance();
                        setStoreCouponList();
                        FxmlUtilities.sceneTransiton(loginButton,"interfaces/marketplaceInterface.fxml",1280,720);
                    }
                }catch(InexistentUserException exception1){
                    loginMessageLabel.setText(exception1.getMessage());
                }catch (BannedUserException exception2){
                    FxmlUtilities.setSanctionPopUp(userTextField.getText());
                }catch(UserPasswordInvalidException exception3){
                    loginMessageLabel.setText(exception3.getMessage());
                }

            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
                Platform.exit();
            }
        }else {
            loginMessageLabel.setText("Please enter username and password.");
        }
    }


}
