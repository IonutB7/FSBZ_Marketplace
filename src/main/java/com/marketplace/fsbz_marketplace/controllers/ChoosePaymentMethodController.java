package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.exceptions.InsufficientAmountException;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ChoosePaymentMethodController {
    @FXML
    private Label chooseMethodLabel;
    @FXML
    private Label chooseMethodMessageLabel;
    @FXML
    private Button withMoneyButton;
    @FXML
    private Button withItemsButton;
    @FXML
    private Button cancelOrderButton;

    public void setCancelOrderButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(cancelOrderButton,"marketplaceInterface.fxml",600,700);
    }

    public void setWithMoneyButtonOnAction(ActionEvent event) throws IOException{

        try {
            InventoryServices.executePaymentWithMoney();
            FxmlUtilities.sceneTransiton(withMoneyButton,"marketplaceInterface.fxml",600,700);

        }catch (InsufficientAmountException exception){
            chooseMethodMessageLabel.setText(exception.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }
    }


}
