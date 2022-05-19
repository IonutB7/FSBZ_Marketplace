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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MarketplaceInterfaceController {

    @FXML
    private Button myInventoryButton;
    @FXML private Button logOutButton;
    @FXML private Button storeInventoryButton;
    @FXML private Button myWalletButton;
    @FXML private Button ledgerButton;
    @FXML private Button helpButton;

    @FXML private Button legerButton;


    public void setMyInventoryButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(storeInventoryButton,"interfaces/userInventory.fxml",1280,720);
    }

    public void setStoreInventoryButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(myInventoryButton,"interfaces/storeInventory.fxml",1280,720);
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(logOutButton,"interfaces/chooseAccountType.fxml",1280,720);
    }

    public void setMyWalletButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(myWalletButton,"interfaces/userWallet.fxml",1280,720);
    }

    public void setLedgerButtonOnAction(ActionEvent event) throws IOException{
        FxmlUtilities.sceneTransiton(ledgerButton,"interfaces/userTransactionHistory.fxml",1280,720);

    }

    public void setHelpButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(helpButton,"interfaces/helpSectionController.fxml",1280,630);
    }


}
