package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.AdminStoreControllerHolder;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.StoreInventoryHolder;
import com.marketplace.fsbz_marketplace.model.WeaponsTableHolder;
import com.marketplace.fsbz_marketplace.services.AdminService;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminAddItemController implements Initializable
{
    @FXML
    private ComboBox<String> weaponNameComboBox;
    @FXML
    private ComboBox<String> weaponWearComboBox;
    @FXML
    private ComboBox<String> weaponStatTrackComboBox;
    @FXML
    private Button createItemButton;
    @FXML
    private Button goBackButton;
    @FXML
    private Label addItemMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> weaponsNames=FXCollections.observableArrayList(new ArrayList<>());
        ObservableList<String> weaponsWears=FXCollections.observableArrayList(new ArrayList<>());
        ObservableList<String> weaponsStatTrack=FXCollections.observableArrayList(new ArrayList<>());
        InventoryServices.setComboBoxFields(weaponsNames,weaponsWears,weaponsStatTrack);
        weaponNameComboBox.setItems(weaponsNames);
        weaponWearComboBox.setItems(weaponsWears);
        weaponStatTrackComboBox.setItems(weaponsStatTrack);
    }

    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(goBackButton,"interfaces/adminStoreInventory.fxml",1280,720);
    }

    public void setCreateItemButtonOnAction(ActionEvent event){
        if(weaponNameComboBox.getValue()!=null&&weaponWearComboBox.getValue()!=null&&weaponStatTrackComboBox.getValue()!=null){
            String name =weaponNameComboBox.getValue();
            String wear = weaponWearComboBox.getValue();
            String category = InventoryServices.returnWeaponCategory(name);
            String description = InventoryServices.returnWeaponDescription(name);
            Boolean statTrack = (weaponStatTrackComboBox.getValue().equals("True")) ? true : false;
            float price = InventoryServices.calculateItemPrice(name,wear,statTrack);
            InventoryServices.addItemToStoreDB(name,category,description,wear,price,statTrack);

            Item tempItem = new Item();
            tempItem.setItemNumber(InventoryServices.retriveLastItemId());
            tempItem.setName(name);
            tempItem.setInventoryId(0);
            tempItem.setDescription(description);
            tempItem.setWear(wear);
            tempItem.setPrice(price);
            tempItem.setCategory(category);
            tempItem.setStatTrack(statTrack);



            StoreInventoryHolder.getInstance().getStoreInventory().add(tempItem);
            AdminStoreControllerHolder.getInstance().getAdminStoreInventoryController().UpdateTable();


        }else {
            addItemMessage.setText("Please select all fields!");
        }
    }

}
