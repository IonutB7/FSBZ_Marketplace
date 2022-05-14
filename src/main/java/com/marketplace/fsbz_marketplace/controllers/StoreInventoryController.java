package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
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
    @FXML private TableColumn<Item,Boolean> storeStatTrackColumn;
    @FXML
    private Button storeGoBackButton;

    @FXML
    private Button checkoutButton;
    @FXML
    private Button userWalletButton;

    public void setSelectedItemsList(){
        ObservableList<Item> selectedItems = storeInventoryTableView.getSelectionModel().getSelectedItems();
        SelectedItemsHolder holder = SelectedItemsHolder.getInstance();
        holder.setSelectedItemsList(selectedItems);
    }
    public void setUserWalletButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(userWalletButton,"userWallet.fxml",600,700);
    }
    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(storeGoBackButton,"marketplaceInterface.fxml",600,700);
    }


    public void setCheckoutButtonOnAction(ActionEvent event)  throws IOException {
            setSelectedItemsList();
            FxmlUtilities.sceneTransiton(checkoutButton,"paymentMethod.fxml",600,700);
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
                                                    storeStatTrackColumn);

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
