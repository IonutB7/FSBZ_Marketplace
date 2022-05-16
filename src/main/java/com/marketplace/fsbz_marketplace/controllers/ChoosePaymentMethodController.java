package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientAmountException;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
        FxmlUtilities.sceneTransiton(cancelOrderButton,"interfaces/marketplaceInterface.fxml",1280 ,720);
    }

    public void setWithMoneyButtonOnAction(ActionEvent event){

        try {
            InventoryServices.executePaymentWithMoney();
            FxmlUtilities.sceneTransiton(withMoneyButton,"interfaces/marketplaceInterface.fxml",1280,720);

        }catch (InsufficientAmountException exception){
            chooseMethodMessageLabel.setText(exception.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }
    }

    public void setWithItemsButtonOnAction(ActionEvent event) throws IOException{
        Stage stage = (Stage) withItemsButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("interfaces/userInventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        MyInventoryController myIC = fxmlLoader.getController();
        myIC.setTransitionFromPaymentMethod(true);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }

}
