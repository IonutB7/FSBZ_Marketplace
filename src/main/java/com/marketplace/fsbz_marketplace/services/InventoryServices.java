package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.User;

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

}
