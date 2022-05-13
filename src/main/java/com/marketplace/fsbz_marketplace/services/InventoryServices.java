package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.StoreInventoryHolder;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class InventoryServices {

    public static void initializeUserInventory(User currentUser, int inventoryId) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveUserItems = "SELECT * FROM user_inventory WHERE inventory_id =" + "'" + inventoryId + "';";

        ArrayList<Item> retrivedInventoryList=new ArrayList<>();


        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveUserItems);

            while (queryResult.next()) {

                Item tempItem = new Item();

                tempItem.setItemNumber(queryResult.getInt("item_number"));
                tempItem.setInventoryId(queryResult.getInt("inventory_id"));
                tempItem.setName(queryResult.getString("name"));
                tempItem.setDescription(queryResult.getString("description"));
                tempItem.setCategory(queryResult.getString("category"));
                tempItem.setWear(queryResult.getString("wear"));
                tempItem.setPrice(queryResult.getInt("price"));
                tempItem.setQuantity(queryResult.getInt("quantity"));

                retrivedInventoryList.add(tempItem);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        currentUser.setUserInventory(retrivedInventoryList);

    }


    public static void initializeStoreInventory(ArrayList<Item> retrivedStoreItems) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveStoreItems = "SELECT * FROM user_inventory WHERE inventory_id ='0'";

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveStoreItems);

            while (queryResult.next()) {

                Item tempItem = new Item();

                tempItem.setItemNumber(queryResult.getInt("item_number"));
                tempItem.setInventoryId(queryResult.getInt("inventory_id"));
                tempItem.setName(queryResult.getString("name"));
                tempItem.setDescription(queryResult.getString("description"));
                tempItem.setCategory(queryResult.getString("category"));
                tempItem.setWear(queryResult.getString("wear"));
                tempItem.setPrice(queryResult.getInt("price"));
                tempItem.setQuantity(queryResult.getInt("quantity"));

                retrivedStoreItems.add(tempItem);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }


    public static ObservableList<Item> getStoreItems(){
        return FXCollections.observableArrayList(StoreInventoryHolder.getInstance().getStoreInventory());
    }

    public static ObservableList<Item> getUserItems(){
        return FXCollections.observableArrayList(UserHolder.getInstance().getUser().getUserInventory());
    }

    public static void setInventoryTableCollumns( TableColumn<Item,Integer> itemNumberColumn,
                                                  TableColumn<Item,Integer> inventoryIdColumn,
                                                  TableColumn<Item,String> nameColumn,
                                                  TableColumn<Item,String> descriptionColumn,
                                                  TableColumn<Item,String> categoryColumn,
                                                  TableColumn<Item,String> wearColumn,
                                                  TableColumn<Item,Float> priceColumn,
                                                  TableColumn<Item,Integer> quantityColumn){
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
        inventoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        wearColumn.setCellValueFactory(new PropertyValueFactory<>("wear"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }


}
