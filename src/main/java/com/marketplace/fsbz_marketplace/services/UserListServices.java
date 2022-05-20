package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.StoreInventoryHolder;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserListHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserListServices {


    public static void banUserDB(int userAcountId,String banContent) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();


        String setUserAsBanned = "UPDATE user_account SET banned=1 WHERE account_id ="+userAcountId+";";
        String setBanContent = "UPDATE user_account SET content ='"+banContent+"' WHERE account_id ="+userAcountId+";";

        try {

            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(setUserAsBanned);
            statement.executeUpdate(setBanContent);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void sendUserWarningDB(int userAcountId,String warningContent) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();


        String setUserAsWarned = "UPDATE user_account SET warned =1 WHERE account_id ="+userAcountId+";";
        String setWarningContent = "UPDATE user_account SET content ='"+warningContent+"' WHERE account_id ="+userAcountId+";";

        try {

            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(setUserAsWarned);
            statement.executeUpdate(setWarningContent);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void clarUserDB(int userAcountId) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();


        String clearBan = "UPDATE user_account SET banned=0 WHERE account_id ="+userAcountId+";";
        String clearWarning = "UPDATE user_account SET warned=0 WHERE account_id ="+userAcountId+";";
        String clearContent = "UPDATE user_account SET content='' WHERE account_id ="+userAcountId+";";

        try {

            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(clearBan);
            statement.executeUpdate(clearWarning);
            statement.executeUpdate(clearContent);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void setWarningForUser(int accId){
        for(int i=0;i<UserListHolder.getInstance().getUserList().size();i++){
            if(UserListHolder.getInstance().getUserList().get(i).getAcountId()==accId){
                UserListHolder.getInstance().getUserList().get(i).setWarned(true);
                return;
            }
        }
    }

    public static User setBanUser(int accId){
        User tempUser=null;
        for(int i=0;i<UserListHolder.getInstance().getUserList().size();i++){
            if(UserListHolder.getInstance().getUserList().get(i).getAcountId()==accId){
                tempUser =UserListHolder.getInstance().getUserList().get(i);
                tempUser.setBanned(true);
            }
        }

        return tempUser;
    }

    public static void setUnbanUser(User restoredUser){
        for(User bannedUser:UserListHolder.getInstance().getBannedUserList()){
            if(bannedUser.getAcountId()==restoredUser.getAcountId()){
                bannedUser.setBanned(false);
                bannedUser.setWarned(false);
                return;
            }
        }
    }
    public static void transferUserToBanList(User bannedUser){
        UserListHolder.getInstance().getBannedUserList().add(bannedUser);
        UserListHolder.getInstance().getUserList().remove(bannedUser);
    }

    public static void transferUserFromBanList(User restoredUser){
        UserListHolder.getInstance().getUserList().add(restoredUser);
        UserListHolder.getInstance().getBannedUserList().remove(restoredUser);
    }

    public static void initializeUserList(ArrayList<User> retrivedUsers,ArrayList<User> retrivedBannedUsers) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveUserList = "SELECT * FROM user_account;";

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveUserList);

            while (queryResult.next()) {

                User tempUser = new User();

                tempUser.setAcountId(queryResult.getInt("account_id"));
                tempUser.setInventoryId(queryResult.getInt("inventory_id"));
                tempUser.setUsername(queryResult.getString("username"));
                tempUser.setEmail(queryResult.getString("email"));
                tempUser.setBalance(queryResult.getFloat("balance"));
                tempUser.setWarned(queryResult.getBoolean("warned"));
                tempUser.setBanned(queryResult.getBoolean("banned"));

                if(tempUser.isBanned()){
                    retrivedBannedUsers.add(tempUser);
                }else{
                    retrivedUsers.add(tempUser);
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public static boolean verifySearchColumns(User user,String lowerCaseFilter){

        if(String.valueOf(user.getAcountId()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(user.getInventoryId()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (user.getUsername().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (user.getEmail().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(user.getBalance()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(user.isWarned()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else
            return false;
    }

    public static ObservableList<User> getUserList(){
        return FXCollections.observableArrayList(UserListHolder.getInstance().getUserList());
    }

    public static ObservableList<User> getBannedUserList(){
        return FXCollections.observableArrayList(UserListHolder.getInstance().getBannedUserList());
    }

    public static void setUserTableCollumns(TableColumn<Item,Integer> userListAccountIdColumn,
                                            TableColumn<Item,Integer> userListInventoryIdColumn,
                                            TableColumn<Item,String> userListUsernameColumn,
                                            TableColumn<Item,String> userListEmailColumn,
                                            TableColumn<Item,Float> userListBalanceColumn,
                                            TableColumn<Item,Boolean> userListWarnedColumn){
        userListAccountIdColumn.setCellValueFactory(new PropertyValueFactory<>("acountId"));
        userListInventoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        userListUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userListEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userListBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        userListWarnedColumn.setCellValueFactory(new PropertyValueFactory<>("warned"));
    }


}
