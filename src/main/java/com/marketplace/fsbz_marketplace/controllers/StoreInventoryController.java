package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.StoreInventoryHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StoreInventoryController implements Initializable {

    @FXML
    private TableView<Item> storeInventoryTableView;

    @FXML private TableColumn<Item,Integer> storeItemNumberColumn;
    @FXML private TableColumn<Item,Integer> storeInventoryIdColumn;
    @FXML private TableColumn<Item,String> storeNameColumn;
    @FXML private TableColumn<Item,String> storeDescriptionColumn;
    @FXML private TableColumn<Item,String> storeCategoryColumn;
    @FXML private TableColumn<Item,String> storeWearColumn;
    @FXML private TableColumn<Item,Float> storePriceColumn;
    @FXML private TableColumn<Item,Integer> storeQuantityColumn;
    @FXML
    private Button storeGoBackButton;

    @FXML
    private Button checkoutButton;
    @FXML
    private Button userWalletButton;

    public void setUserWalletButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) userWalletButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("userWallet.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }
    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        Stage stage = (Stage) storeGoBackButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("marketplaceInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }


    public void setCheckoutButtonOnAction(ActionEvent event)  throws IOException {
        ObservableList<Item> selectedItems = storeInventoryTableView.getSelectionModel().getSelectedItems();
        System.out.println(selectedItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        InventoryServices.setInventoryTableCollumns(storeItemNumberColumn,
                                                    storeInventoryIdColumn,
                                                    storeNameColumn,
                                                    storeDescriptionColumn,
                                                    storeCategoryColumn,
                                                    storeWearColumn,
                                                    storePriceColumn,
                                                    storeQuantityColumn);

        storeInventoryTableView.setItems(InventoryServices.getStoreItems());
        storeInventoryTableView.setOnMouseClicked(event ->
                storeInventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE));

        storeInventoryTableView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            while (node != null && node != storeInventoryTableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            if (node instanceof TableRow) {
                evt.consume();

                TableRow row = (TableRow) node;
                TableView tv = row.getTableView();

                tv.requestFocus();

                if (!row.isEmpty()) {
                    int index = row.getIndex();
                    if (row.isSelected()) {
                        tv.getSelectionModel().clearSelection(index);
                    } else {
                        tv.getSelectionModel().select(index);
                    }
                }
            }
        });

    }




}
