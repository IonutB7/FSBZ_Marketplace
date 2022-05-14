package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MyInventoryController implements Initializable {

    @FXML
    private TableView<Item> userInventoryTableView;

    @FXML private TableColumn<Item,Integer> userItemNumberColumn;
    @FXML private TableColumn<Item,Integer> userInventoryIdColumn;
    @FXML private TableColumn<Item,String> userNameColumn;
    @FXML private TableColumn<Item,String> userDescriptionColumn;
    @FXML private TableColumn<Item,String> userCategoryColumn;
    @FXML private TableColumn<Item,String> userWearColumn;
    @FXML private TableColumn<Item,Float> userPriceColumn;
    @FXML private TableColumn<Item,Boolean> userStatTrackColumn;

    @FXML
    private Button userGoBackButton;
    @FXML
    private Button userWalletButton;

    public void setGoBackButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(userGoBackButton,"marketplaceInterface.fxml",600,700);
    }

    public void setUserWalletButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(userWalletButton,"userWallet.fxml",600,700);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        InventoryServices.setInventoryTableCollumns(userItemNumberColumn,
                                                    userInventoryIdColumn,
                                                    userNameColumn,
                                                    userDescriptionColumn,
                                                    userCategoryColumn,
                                                    userWearColumn,
                                                    userPriceColumn,
                                                    userStatTrackColumn);

        userInventoryTableView.setItems(InventoryServices.getUserItems());
    }

}
