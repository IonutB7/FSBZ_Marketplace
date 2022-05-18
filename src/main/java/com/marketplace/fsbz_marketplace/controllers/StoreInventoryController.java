package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.exceptions.StoreItemsNotSelectedException;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
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
    private TextField storeSearchTextField;

    @FXML
    private Label storeSearchLabel;
    @FXML
    private Label storeTotalValueLabel;
    @FXML
    private Label storeTotalValueMessage;
    @FXML
    private Label checkOutMessageLabel;
    @FXML
    private Button checkOutButton;
    @FXML
    private Button userWalletButton;

    public void setStoreSelectedItemsList(){
        ObservableList<Item> selectedItems=storeInventoryTableView.getSelectionModel().getSelectedItems();
        SelectedItemsHolder holder = SelectedItemsHolder.getInstance();
        holder.setSelectedItemsStoreInventory(selectedItems);
        float totalValue=InventoryServices.calculateTotalItemValue(selectedItems);
        holder.setTotalValueStoreItems(totalValue+(5*totalValue)/100);
    }
    public void setUserWalletButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton2("interfaces/userWallet.fxml",520,530);
    }
    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(storeGoBackButton,"interfaces/marketplaceInterface.fxml",1280,720);
    }



    public void setCheckOutButtonOnAction(ActionEvent event)  throws IOException {
        try{
            if(storeInventoryTableView.getSelectionModel().getSelectedItems().size()!=0){
                FxmlUtilities.sceneTransiton(checkOutButton,"interfaces/paymentMethod.fxml",520,530);
            }else{
                throw new StoreItemsNotSelectedException("No items selected!");
            }

        }catch (StoreItemsNotSelectedException exception){
            checkOutMessageLabel.setText(exception.getMessage());
        }
    }

    public void setCheckOutButtonOnAction1(MouseEvent event)  throws IOException {
        try{
            if(storeInventoryTableView.getSelectionModel().getSelectedItems().size()!=0){
                FxmlUtilities.sceneTransiton(checkOutButton,"interfaces/paymentMethod.fxml",520 ,530);
            }else{
                throw new StoreItemsNotSelectedException("No items selected!");
            }

        }catch (StoreItemsNotSelectedException exception){
            checkOutMessageLabel.setText(exception.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        InventoryServices services = new InventoryServices();
        services.resetSelectedStoreItem();

        storeTotalValueLabel.textProperty().bind(Bindings.convert(SelectedItemsHolder.getInstance().totalValueStoreItemsProperty()));

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
