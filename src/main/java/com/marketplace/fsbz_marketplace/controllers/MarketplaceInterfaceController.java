package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.services.UserListServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MarketplaceInterfaceController implements Initializable {

    @FXML
    private Label userHelloMessageLabel;
    @FXML
    private Button myInventoryButton;
    @FXML private Button logOutButton;
    @FXML private Button storeInventoryButton;
    @FXML private Button myWalletButton;
    @FXML private Button ledgerButton;
    @FXML private Button helpButton;

    @FXML private Button reportButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        userHelloMessageLabel.setText("hi "+UserHolder.getInstance().getUser().getUsername());

    }

    public void setReportSectionButtonOnAction()throws IOException{
        FxmlUtilities.sceneTransiton(reportButton,"interfaces/reportSection.fxml",520,530);

    }
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
        FxmlUtilities.sceneTransiton2("interfaces/userWallet.fxml",520,530);
    }

    public void setLedgerButtonOnAction(ActionEvent event) throws IOException{
        Stage stage = (Stage) ledgerButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("interfaces/userTransactionHistory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        TransactionHistoryController myTHC = fxmlLoader.getController();
        myTHC.setUsedByUser(true);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();

    }

    public void setLedgerButtonOnAction1(MouseEvent event) throws IOException{
        FxmlUtilities.sceneTransiton(ledgerButton,"interfaces/userTransactionHistory.fxml",1280,720);

    }

    public void setHelpButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(helpButton,"interfaces/helpSectionController.fxml",1280,720);
    }

    public void setHelpButtonOnAction1(MouseEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(helpButton,"interfaces/helpSectionController.fxml",1280,720);
    }


}
