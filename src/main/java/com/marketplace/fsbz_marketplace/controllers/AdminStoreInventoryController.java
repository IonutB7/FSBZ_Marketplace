package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminStoreInventoryController implements Initializable {

    @FXML
    private TextField storeSearchTextField;

    @FXML
    private Label storeSearchLabel;

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


    public void setStoreSelectedItemsList(){
        ObservableList<Item> selectedItems=storeInventoryTableView.getSelectionModel().getSelectedItems();
        SelectedItemsHolder holder = SelectedItemsHolder.getInstance();
        holder.setSelectedItemsStoreInventory(selectedItems);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        InventoryServices services = new InventoryServices();
        services.resetSelectedStoreItem();

        InventoryServices.setInventoryTableCollumns(storeItemNumberColumn,
                storeInventoryIdColumn,
                storeNameColumn,
                storeDescriptionColumn,
                storeCategoryColumn,
                storeWearColumn,
                storePriceColumn,
                storeStatTrackColumn);

        storeInventoryTableView.setItems(InventoryServices.getStoreItems());

        storeInventoryTableView.setOnMouseClicked(event -> {
            storeInventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setStoreSelectedItemsList();
            }
        });

        FxmlUtilities.setMultipleSelctionModeEnable(storeInventoryTableView);
        FilteredList<Item> filteredData = new FilteredList<>(InventoryServices.getStoreItems(), b -> true);

        storeSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(item -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return InventoryServices.verifySearchColumns(item,lowerCaseFilter);
        }));


        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(storeInventoryTableView.comparatorProperty());
        storeInventoryTableView.setItems(sortedData);

    }

}
