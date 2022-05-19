package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.exceptions.StoreItemsNotSelectedException;
import com.marketplace.fsbz_marketplace.model.AdminStoreControllerHolder;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminStoreInventoryController implements Initializable {


    @FXML
    private TextField storeSearchTextField;

    @FXML
    private Label storeSearchLabel;
    @FXML
    private Label storeErrorMessageLabel;

    @FXML
    private TableView<Item> storeInventoryTableView;
    @FXML
    private Button goBackButton;
    @FXML
    private Button deleteItemsButton;
    @FXML
    private Button addItemButton;
    @FXML
    private AnchorPane addItemAnchor;

    @FXML private TableColumn<Item,Integer> storeItemNumberColumn;
    @FXML private TableColumn<Item,Integer> storeInventoryIdColumn;
    @FXML private TableColumn<Item,String> storeNameColumn;
    @FXML private TableColumn<Item,String> storeDescriptionColumn;
    @FXML private TableColumn<Item,String> storeCategoryColumn;
    @FXML private TableColumn<Item,String> storeWearColumn;
    @FXML private TableColumn<Item,Float> storePriceColumn;
    @FXML private TableColumn<Item,Boolean> storeStatTrackColumn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        InventoryServices services = new InventoryServices();
        services.resetSelectedStoreItem();

        UpdateTable();

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

    public void UpdateTable(){
        InventoryServices.setInventoryTableCollumns(storeItemNumberColumn,
                storeInventoryIdColumn,
                storeNameColumn,
                storeDescriptionColumn,
                storeCategoryColumn,
                storeWearColumn,
                storePriceColumn,
                storeStatTrackColumn);

        storeInventoryTableView.setItems(InventoryServices.getStoreItems());
    }

    public void setStoreSelectedItemsList(){
        ObservableList<Item> selectedItems=storeInventoryTableView.getSelectionModel().getSelectedItems();
        SelectedItemsHolder holder = SelectedItemsHolder.getInstance();
        holder.setSelectedItemsStoreInventory(selectedItems);
    }

    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(goBackButton,"interfaces/adminMainInterface.fxml",1280,720);
    }

    public void setAddItemButton(ActionEvent event) throws IOException{
        FxmlUtilities.sceneTransiton1(addItemAnchor,"interfaces/adminAddItem.fxml",520,530);
    }

    public void setAddItemButton1(MouseEvent event) throws IOException{
        FxmlUtilities.sceneTransiton1(addItemAnchor,"interfaces/adminAddItem.fxml",520,530);
    }


    public void setDeleteItemsButtonButtonOnAction(ActionEvent event)  throws IOException {
        try{
            if(storeInventoryTableView.getSelectionModel().getSelectedItems().size()!=0){
                InventoryServices.removeStoreSelectedItems(SelectedItemsHolder.getInstance().getSelectedItemsStoreInventory());
                InventoryServices.deleteSelectedItems(SelectedItemsHolder.getInstance().getSelectedItemsStoreInventory());
                UpdateTable();
            }else{
                throw new StoreItemsNotSelectedException("No items selected!");
            }

        }catch (StoreItemsNotSelectedException exception){
            storeErrorMessageLabel.setText(exception.getMessage());
        }
    }

}
