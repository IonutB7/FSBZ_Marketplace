package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientAmountException;
import com.marketplace.fsbz_marketplace.exceptions.WalletExceptions;
import com.marketplace.fsbz_marketplace.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class InventoryServices {

    public static boolean exitsItemInUserInventory(Item item){
        ArrayList<Item> userItems = UserHolder.getInstance().getUser().getUserInventory();
        for(Item it:userItems){
            if(item.getItemNumber()==it.getItemNumber())
                return true;
        }
        return false;
    }

    public static void transferSelectedItemsToUserDB(int userInventoryId,ObservableList<Item> selectedItems) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String itemsIdList = "(";

        for (int i = 0; i < selectedItems.size(); i++) {
                if (i != selectedItems.size() - 1) {
                    itemsIdList += "'" + selectedItems.get(i).getInventoryId() + "',";
                } else {
                    itemsIdList += "'" + selectedItems.get(i).getInventoryId() + "'";
                }

            }

            itemsIdList += ")";

            String transferSelectedItems = "UPDATE user_inventory SET inventory_id =" + userInventoryId + " WHERE inventory_id IN " + itemsIdList + ";";

            try {

                Statement statement = connectionDB.createStatement();
                statement.executeUpdate(transferSelectedItems);
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }

    public static void initializeUserInventory(User currentUser, int inventoryId) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retrieveUserItems = "SELECT * FROM user_inventory WHERE inventory_id =" + "'" + inventoryId + "';";

        ArrayList<Item> retrievedInventoryList=new ArrayList<>();


        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retrieveUserItems);

            while (queryResult.next()) {

                Item tempItem = new Item();

                tempItem.setItemNumber(queryResult.getInt("item_number"));
                tempItem.setInventoryId(queryResult.getInt("inventory_id"));
                tempItem.setName(queryResult.getString("name"));
                tempItem.setDescription(queryResult.getString("description"));
                tempItem.setCategory(queryResult.getString("category"));
                tempItem.setWear(queryResult.getString("wear"));
                tempItem.setPrice(queryResult.getFloat("price"));
                tempItem.setWeaponTag(queryResult.getInt("weapon_tag"));
                tempItem.setStatTrack(queryResult.getBoolean("stat_track"));

                retrievedInventoryList.add(tempItem);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        currentUser.setUserInventory(retrievedInventoryList);

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
                tempItem.setPrice(queryResult.getFloat("price"));
                tempItem.setWeaponTag(queryResult.getInt("weapon_tag"));
                tempItem.setStatTrack(queryResult.getBoolean("stat_track"));

                retrivedStoreItems.add(tempItem);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public static void removeSelectedItems(ObservableList<Item> selectedItems){
        for(Item item:selectedItems){
            StoreInventoryHolder.getInstance().getStoreInventory().remove(item);
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
                                                  TableColumn<Item,Boolean> statTrackColumn){
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
        inventoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        wearColumn.setCellValueFactory(new PropertyValueFactory<>("wear"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statTrackColumn.setCellValueFactory(new PropertyValueFactory<>("statTrack"));
    }



    public static void executePaymentWithMoney()throws WalletExceptions {

        ObservableList<Item> selectedItems = SelectedItemsHolder.getInstance().getSelectedItemsList();
        float valueItems=0;
        for(Item item:selectedItems){
            valueItems+=item.getPrice();
        }
        float commission = (5*valueItems)/100;
        float totalValue =valueItems+commission;
        if(UserHolder.getInstance().getUser().getBalance()>=totalValue){
            float newBalanceValue = UserHolder.getInstance().getUser().getBalance()-totalValue;
            int currentUserAccountId = UserHolder.getInstance().getUser().getAcountId();
            UserHolder.getInstance().getUser().setBalance(newBalanceValue);
            UserServices.updateUserBalance(newBalanceValue,currentUserAccountId);
            transferSelectedItemsToUserDB(UserHolder.getInstance().getUser().getInventoryId(),selectedItems);
            UserServices.addSelectedItems(selectedItems);
            removeSelectedItems(selectedItems);
        }else{
            throw new InsufficientAmountException("Insufficient money in wallet!");
        }
    }




}
