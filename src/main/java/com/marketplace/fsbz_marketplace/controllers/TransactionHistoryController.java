package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.model.Transaction;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.services.LedgerService;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionHistoryController implements Initializable {
    @FXML
    private TableView<Transaction> userLedgerTableView;
    @FXML private TableColumn<Transaction,Integer> transactionNumberColumn;
    @FXML private TableColumn<Transaction,Integer> inventoryIdColumn;
    @FXML private TableColumn<Transaction,String> userOfferColumn;
    @FXML private TableColumn<Transaction,String> storeOfferColumn;
    @FXML private TableColumn<Transaction,Float> totalPriceColumn;
    @FXML private TableColumn<Transaction,String> transactionTypeColumn;
    @FXML private TableColumn<Transaction, Timestamp> dateColumn;
    @FXML private TableColumn<Transaction,String> statusColumn;


    @FXML
    private TextField searchTextField;

    @FXML
    private Label searchLabel;

    @FXML
    private Button helpButton;
    @FXML
    private Button walletButton;
    @FXML
    private Button goBackButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        InventoryServices services = new InventoryServices();
        services.resetSelectedUserItem();


        LedgerService.setLedgerTableCollumns(transactionNumberColumn,
                inventoryIdColumn,
                userOfferColumn,
                storeOfferColumn,
                totalPriceColumn,
                transactionTypeColumn,
                dateColumn,
                statusColumn);

        userLedgerTableView.setItems(LedgerService.getUserTransactions());


        FilteredList<Transaction> filteredData = new FilteredList<>(LedgerService.getUserTransactions(), b -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(transaction -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return LedgerService.verifySearchColumns(transaction,lowerCaseFilter);
        }));


        SortedList<Transaction> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userLedgerTableView.comparatorProperty());
        userLedgerTableView.setItems(sortedData);
    }

    public void setGoBackButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(goBackButton,"interfaces/marketplaceInterface.fxml",1280,720);
    }

    public void setWalletButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(walletButton,"interfaces/userWallet.fxml",1280,720);
    }
}
