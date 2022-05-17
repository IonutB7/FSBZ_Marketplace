package com.marketplace.fsbz_marketplace.controllers;


import com.marketplace.fsbz_marketplace.exceptions.InexistentCouponException;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientAmountException;
import com.marketplace.fsbz_marketplace.exceptions.InvalidCouponException;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.services.WalletServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WalletController implements Initializable {
    @FXML
    private Button storeGoBackButton;
    @FXML
    private Button validateCodeButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Label codeMessageLabel;
    @FXML
    private Label withdrawMessageLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label balanceValueLabel;
    @FXML
    private Label enterCodeLabel;
    @FXML
    private Label enterAmountLabel;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField amountTextField ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balanceValueLabel.textProperty().bind(Bindings.convert(UserHolder.getInstance().getUser().balanceProperty()));
    }

    public void withdrawButtonOnAction(ActionEvent event) throws IOException{
            try{
                WalletServices.withdrawAmount(Float.parseFloat(amountTextField.getText()));
            }catch(InsufficientAmountException exception){
                withdrawMessageLabel.setText(exception.getMessage());
            }catch(Exception e){
                e.printStackTrace();
                e.getCause();
                Platform.exit();
            }
    }
    public void withdrawButtonOnAction1(MouseEvent event) throws IOException{
        try{
            WalletServices.withdrawAmount(Float.parseFloat(amountTextField.getText()));
        }catch(InsufficientAmountException exception){
            withdrawMessageLabel.setText(exception.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }
    }

    public void validateCodeButtonOnAction(ActionEvent event) throws IOException{
        try {
            WalletServices.verifyCoupon(codeTextField.getText());
        }catch (InvalidCouponException exception1){
            codeMessageLabel.setText(exception1.getMessage());

        } catch (InexistentCouponException exception2) {
            codeMessageLabel.setText(exception2.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }

    }

    public void validateCodeButtonOnAction1(MouseEvent event) throws IOException{
        try {
            WalletServices.verifyCoupon(codeTextField.getText());
        }catch (InvalidCouponException exception1){
            codeMessageLabel.setText(exception1.getMessage());

        } catch (InexistentCouponException exception2) {
            codeMessageLabel.setText(exception2.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }

    }

    public void setGoBackButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton3(storeGoBackButton);
    }

    public void setGoBackButtonOnAction1(MouseEvent event) throws IOException {
        FxmlUtilities.sceneTransiton3(storeGoBackButton);
    }
}
