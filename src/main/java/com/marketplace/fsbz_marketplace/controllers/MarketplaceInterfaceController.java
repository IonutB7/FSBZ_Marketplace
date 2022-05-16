package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MarketplaceInterfaceController {

    @FXML
    private Button myInventoryButton;
    @FXML private Button logOutButton;
    @FXML private Button storeInventoryButton;

    @FXML private Button myWalletButton;

    @FXML private Button legerButton;


    public void setMyInventoryButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(storeInventoryButton,"userInventory.fxml",600,700);
    }

    public void setStoreInventoryButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(myInventoryButton,"storeInventory.fxml",600,700);
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(logOutButton,"userLogIn.fxml",600,700);
    }

    public void setMyWalletButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(myWalletButton,"userWallet.fxml",600,700);
    }

    public void setLegerButtonOnAction(ActionEvent event) throws IOException{
        FxmlUtilities.sceneTransiton(legerButton,"userTransactionHistory.fxml",600,700);

    }

}
