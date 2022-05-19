package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.BannedUserException;
import com.marketplace.fsbz_marketplace.exceptions.CredentialsExceptions;
import com.marketplace.fsbz_marketplace.exceptions.InexistentUserException;
import com.marketplace.fsbz_marketplace.exceptions.UserPasswordInvalidException;
import com.marketplace.fsbz_marketplace.model.*;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LedgerService {

    public static void addTransactionToLedger(Transaction transaction){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();


        String insertFields = "INSERT INTO transaction_db(inventory_id,user_offer,store_offer,total_price,transaction_type,date,status) VALUES ('";
        String insertValues = transaction.getInventoryId()+"','"+transaction.getUserOffer()+"','" +transaction.getStoreOffer()+"','" +transaction.getTotalPrice()+"','" +transaction.getTransactionType()+"','" +transaction.getTransactionDate()+"','"+transaction.getStatus()+"')";
        String addTransactionToLedger = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(addTransactionToLedger);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void initializeUserLedger(User currentUser, int inventoryId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retrieveUserTransaction = "SELECT * FROM transaction_db WHERE inventory_id =" + "'" + inventoryId + "';";

        ArrayList<Transaction> retrievedTransactionList = new ArrayList<>();


        try {

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retrieveUserTransaction);

            while (queryResult.next()) {

                Transaction tempTransaction = new Transaction();

                tempTransaction.setTransactionId(queryResult.getInt("transaction_id"));
                tempTransaction.setInventoryId(queryResult.getInt("inventory_id"));
                tempTransaction.setUserOffer(queryResult.getString("user_offer"));
                tempTransaction.setStoreOffer(queryResult.getString("store_offer"));
                tempTransaction.setTotalPrice(queryResult.getFloat("total_price"));
                tempTransaction.setTransactionType(queryResult.getString("transaction_type"));
                tempTransaction.setTransactionDate(queryResult.getTimestamp("date"));
                tempTransaction.setStatus(queryResult.getString("status"));


                retrievedTransactionList.add(tempTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        currentUser.setUserTransationList(retrievedTransactionList);
    }

    public static void setLedgerTableCollumns( TableColumn<Transaction,Integer> transactionNumberColumn,
                                               TableColumn<Transaction,Integer> inventoryIdColumn,
                                               TableColumn<Transaction,String> userOfferColumn,
                                               TableColumn<Transaction,String> storeOfferColumn,
                                               TableColumn<Transaction,Float> totalPriceColumn,
                                               TableColumn<Transaction,String> transactionTypeColumn,
                                               TableColumn<Transaction, Timestamp> dateColumn,
                                               TableColumn<Transaction,String> statusColumn){
        transactionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        inventoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        userOfferColumn.setCellValueFactory(new PropertyValueFactory<>("userOffer"));
        storeOfferColumn.setCellValueFactory(new PropertyValueFactory<>("storeOffer"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    public static ObservableList<Transaction> getUserTransactions(){
        return FXCollections.observableArrayList(UserHolder.getInstance().getUser().getUserTransationList());
    }

    public static int retriveLastTransactionId() {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveLastTransactionId ="SELECT MAX(transaction_id) FROM transaction_db;";
        int retriveResult=0;

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveLastTransactionId);

            if(queryResult.next()){
                retriveResult=queryResult.getInt(1);
            }else{
                return retriveResult;
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return retriveResult;
    }

    public static Transaction createTransaction(int inventoryId,float totalPrice,String transactionType,String status){

        Transaction transaction = new Transaction();
        java.sql.Timestamp date=new java.sql.Timestamp(new Date().getTime());
        ObservableList<Item> userSelectedItems=SelectedItemsHolder.getInstance().getSelectedItemsUserInventory();
        ObservableList<Item> storeSelectedItems=SelectedItemsHolder.getInstance().getSelectedItemsStoreInventory();
        String userOffer="",storeOffer="";
        transaction.setTransactionId(retriveLastTransactionId());
        transaction.setInventoryId(inventoryId);
        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionType(transactionType);
        transaction.setStatus(status);
        transaction.setTransactionDate(date);

        if(transactionType.equals("selling")) {
            storeOffer = "Sum offered:" + (SelectedItemsHolder.getInstance().getTotalValueUserItems()-5*SelectedItemsHolder.getInstance().getTotalValueUserItems()/100);
            userOffer= "Item id offered:";
            for (int i = 0; i < userSelectedItems.size(); i++) {
                if (i != userSelectedItems.size() - 1) {
                    userOffer += userSelectedItems.get(i).getItemNumber() + ",";
                } else {
                    userOffer += userSelectedItems.get(i).getItemNumber();
                }
            }
        }


        if(transactionType.equals("buying")) {
            userOffer = "Sum offered:" + SelectedItemsHolder.getInstance().getTotalValueStoreItems()*20/21;
            storeOffer = "Item id offered:";
            for (int i = 0; i <storeSelectedItems.size(); i++) {
                if (i != storeSelectedItems.size() - 1) {
                    storeOffer += storeSelectedItems.get(i).getItemNumber() + ",";
                } else {
                    storeOffer += storeSelectedItems.get(i).getItemNumber();
                }
            }
        }


        if(transactionType.equals("trading")) {
            userOffer = "Item id offered:";
            storeOffer = "Item id offered:";

            for (int i = 0; i < userSelectedItems.size(); i++) {
                if (i != userSelectedItems.size() - 1) {
                    userOffer += userSelectedItems.get(i).getItemNumber() + ",";
                } else {
                    userOffer += userSelectedItems.get(i).getItemNumber();
                }
            }

            for (int i = 0; i < storeSelectedItems.size(); i++) {
                if (i != storeSelectedItems.size() - 1) {
                    storeOffer += storeSelectedItems.get(i).getItemNumber() + ",";
                } else {
                    storeOffer += storeSelectedItems.get(i).getItemNumber();
                }
            }
        }



        transaction.setUserOffer(userOffer);
        transaction.setStoreOffer(storeOffer);

        return transaction;
    }

    public static boolean verifySearchColumns(Transaction transaction,String lowerCaseFilter){

        if(String.valueOf(transaction.getTransactionId()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(transaction.getInventoryId()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (transaction.getUserOffer().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (transaction.getStoreOffer().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(transaction.getTotalPrice()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (transaction.getTransactionType().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(transaction.getTransactionDate()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (transaction.getStatus().toLowerCase().contains(lowerCaseFilter))
            return true;
        else
            return false;
    }

}
