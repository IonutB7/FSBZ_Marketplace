package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class MyInventoryController implements Initializable {

    @FXML
    private TableView<Item> inventoryTableView;

    @FXML private TableColumn<Item,Integer> itemNumberColumn;
    @FXML private TableColumn<Item,Integer> inventoryIdColumn;
    @FXML private TableColumn<Item,String> nameColumn;
    @FXML private TableColumn<Item,String> descriptionColumn;
    @FXML private TableColumn<Item,String> categoryColumn;
    @FXML private TableColumn<Item,String> wearColumn;
    @FXML private TableColumn<Item,Float> priceColumn;
    @FXML private TableColumn<Item,Integer> quantityColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
        inventoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        wearColumn.setCellValueFactory(new PropertyValueFactory<>("wear"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        inventoryTableView.setItems(getItems());
    }

    public ObservableList<Item> getItems(){
        ObservableList<Item> observableItemList = FXCollections.observableArrayList(UserHolder.getInstance().getUser().getUserInventory());
        return observableItemList;
    }


}
