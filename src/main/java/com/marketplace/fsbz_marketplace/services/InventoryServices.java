package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientAmountException;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientItemValueException;
import com.marketplace.fsbz_marketplace.exceptions.InventoryExceptions;
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



    public void resetSelectedUserItem(){
        SelectedItemsHolder.getInstance().setSelectedItemsUserInventory(null);
        SelectedItemsHolder.getInstance().setTotalValueUserItems(0);
    }

    public void resetSelectedStoreItem(){
        SelectedItemsHolder.getInstance().setSelectedItemsStoreInventory(null);
        SelectedItemsHolder.getInstance().setTotalValueStoreItems(0);
    }

    public static float calculateTotalItemValue(ObservableList<Item> selectedItems){
        float totalValue=0;
        for(Item item:selectedItems){
            totalValue+=item.getPrice();
        }
        return totalValue;
    }

    public static void transferSelectedItems(int userInventoryId,ObservableList<Item> selectedItems) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String itemsIdList = "(";



        for (int i = 0; i < selectedItems.size(); i++) {
                if (i != selectedItems.size() - 1) {
                    itemsIdList += "'" + selectedItems.get(i).getItemNumber() + "',";
                } else {
                    itemsIdList += "'" + selectedItems.get(i).getItemNumber() + "'";
                }

            }

            itemsIdList += ")";

            String transferSelectedItems = "UPDATE user_inventory SET inventory_id =" + userInventoryId + " WHERE item_number IN " + itemsIdList + ";";

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

    public static void removeStoreSelectedItems(ObservableList<Item> selectedItems){
        for(Item item:selectedItems){
            StoreInventoryHolder.getInstance().getStoreInventory().remove(item);
        }
    }

    public static void addStoreSelectedItems(ObservableList<Item> selectedItems){
        for(Item item:selectedItems){
            StoreInventoryHolder.getInstance().getStoreInventory().add(item);
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

        ObservableList<Item> selectedStoreItems = SelectedItemsHolder.getInstance().getSelectedItemsStoreInventory();
        float totalValueStoreItems =SelectedItemsHolder.getInstance().getTotalValueStoreItems();

        if(UserHolder.getInstance().getUser().getBalance()>=totalValueStoreItems){

            float newBalanceValue = UserHolder.getInstance().getUser().getBalance()-totalValueStoreItems;
            int currentUserAccountId = UserHolder.getInstance().getUser().getAcountId();

            UserHolder.getInstance().getUser().setBalance(newBalanceValue);
            UserServices.updateUserBalance(newBalanceValue,currentUserAccountId);
            InventoryServices.changeItemsInventoryId(selectedStoreItems, UserHolder.getInstance().getUser().getInventoryId());

            transferSelectedItems(currentUserAccountId,selectedStoreItems);
            UserServices.addUserSelectedItems(selectedStoreItems);
            removeStoreSelectedItems(selectedStoreItems);

        }else{
            throw new InsufficientAmountException("Insufficient money in wallet!");
        }
    }

    public static void executePaymentWithItems()throws InventoryExceptions {
        ObservableList<Item> selectedUserItems = SelectedItemsHolder.getInstance().getSelectedItemsUserInventory();
        ObservableList<Item> selectedStoreItems = SelectedItemsHolder.getInstance().getSelectedItemsStoreInventory();
        float totalUserItemsValue =SelectedItemsHolder.getInstance().getTotalValueUserItems();

        if(totalUserItemsValue>=SelectedItemsHolder.getInstance().getTotalValueStoreItems()){

            float compensation =totalUserItemsValue-SelectedItemsHolder.getInstance().getTotalValueStoreItems();
            float newBalanceValue = UserHolder.getInstance().getUser().getBalance()+compensation;
            int currentUserAccountId = UserHolder.getInstance().getUser().getAcountId();

            UserHolder.getInstance().getUser().setBalance(newBalanceValue);
            UserServices.updateUserBalance(newBalanceValue,currentUserAccountId);

            InventoryServices.changeItemsInventoryId(selectedUserItems,0);
            InventoryServices.changeItemsInventoryId(selectedStoreItems, UserHolder.getInstance().getUser().getInventoryId());


            transferSelectedItems(currentUserAccountId,selectedStoreItems);
            UserServices.addUserSelectedItems(selectedStoreItems);
            removeStoreSelectedItems(selectedStoreItems);


            transferSelectedItems(0,selectedUserItems);
            UserServices.removeUserSelectedItems(selectedUserItems);
            addStoreSelectedItems(selectedUserItems);

        }else{
            throw new InsufficientItemValueException("The selected items are not equivalent in value!");
        }
    }

    public static void sellUserItems(){
        ObservableList<Item> selectedUserItems = SelectedItemsHolder.getInstance().getSelectedItemsUserInventory();
        float totalUserItemsValue =SelectedItemsHolder.getInstance().getTotalValueUserItems();



        float commision = 5*totalUserItemsValue/100;
        float newBalanceValue = UserHolder.getInstance().getUser().getBalance()+totalUserItemsValue-commision;
        int currentUserAccountId = UserHolder.getInstance().getUser().getAcountId();

        UserHolder.getInstance().getUser().setBalance(newBalanceValue);
        UserServices.updateUserBalance(newBalanceValue,currentUserAccountId);

        InventoryServices.changeItemsInventoryId(selectedUserItems,0);

        transferSelectedItems(0,selectedUserItems);
        UserServices.removeUserSelectedItems(selectedUserItems);
        addStoreSelectedItems(selectedUserItems);

    }

     public static void changeItemsInventoryId(ObservableList<Item> selectedItems,int inventoryId){
        for(int i=0;i<selectedItems.size();i++){
            selectedItems.get(i).setInventoryId(inventoryId);
        }
     }


     public static boolean verifySearchColumns(Item item,String lowerCaseFilter){

        if(String.valueOf(item.getItemNumber()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(item.getInventoryId()).toLowerCase().contains(lowerCaseFilter))
             return true;
         else if (item.getName().toLowerCase().contains(lowerCaseFilter))
             return true;
         else if (item.getDescription().toLowerCase().contains(lowerCaseFilter))
             return true;
        else if (item.getCategory().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (item.getWear().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(item.getPrice()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(item.getName()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(item.isStatTrack()).toLowerCase().contains(lowerCaseFilter))
            return true;
         else
             return false;
     }

}
