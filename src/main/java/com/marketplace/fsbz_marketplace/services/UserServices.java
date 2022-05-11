package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.*;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class UserServices {


    public static void initializeUser(User currentUser,String username) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveLastUserId = "SELECT * FROM user_account WHERE username =" + "'" + username + "';";

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveLastUserId);

            while (queryResult.next()) {

                int retrivedAcountId=queryResult.getInt("account_id");
                int retrivedInventoryId=queryResult.getInt("inventory_id");
                String retrivedEmail=queryResult.getString("email");
                String retrivedUsername=queryResult.getString("username");


                currentUser.setAcountId(retrivedAcountId);
                currentUser.setInventoryId(retrivedInventoryId);
                currentUser.setEmail(retrivedEmail);
                currentUser.setUsername(retrivedUsername);

            }

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }


    public static int retriveLastUserId(Connection connectionDB) {

        String retriveLastUserId ="SELECT MAX(account_id) FROM user_account;";
        int retriveResult=0;

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveLastUserId);

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

    public static void registerUser(String firstname, String lastname, String email, String username, String saltvalue, String encryptedPass){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        int banned = 0;
        int adminAproved = 1;
        int inventory_id=retriveLastUserId(connectDB);
        inventory_id++;

        String insertFields = "INSERT INTO user_account(inventory_id,firstname,lastname,email,username,encryptedPass,salt,banned,adminAproved) VALUES ('";
        String insertValues = inventory_id+"','"+firstname+"','" +lastname+"','" +email+"','" +username+"','" +encryptedPass+"','"+saltvalue+"','"+banned+"','"+adminAproved+"')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public static boolean validateLogin(String username, String password, Button loginButton)throws CredentialsExceptions,java.sql.SQLException,java.io.IOException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveEncryptedPassStatement ="SELECT encryptedPass,salt,banned FROM user_account WHERE username = '" + username +"'";


            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveEncryptedPassStatement);

            if (!queryResult.isBeforeFirst() ) {
               throw new InexistentUserException("User does not exist!");
            }else {
                while (queryResult.next()) {
                    String proviedPassword = password;
                    String retrivedEncryptedPass = queryResult.getString("encryptedPass");
                    String retrivedSalt = queryResult.getString("salt");
                    int isBanned = queryResult.getInt("banned");
                    if (isBanned == 0) {
                        if (PassBasedEnc.verifyUserPassword(proviedPassword, retrivedEncryptedPass, retrivedSalt) == true) {

                          return true;

                        } else {
                            throw new UserPasswordInvalidException("The username or password is incorrect.Please try again.");
                        }
                    } else {
                        throw new BannedUserException("The user is banned.");
                    }
                }

            }
        return false;
    }
}
