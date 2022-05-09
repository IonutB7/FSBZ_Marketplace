package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.BannedUserException;
import com.marketplace.fsbz_marketplace.exceptions.InexistentUserException;
import com.marketplace.fsbz_marketplace.exceptions.UserPasswordInvalidException;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;
import com.marketplace.fsbz_marketplace.exceptions.CredentialsExceptions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserServices{

    public static void registerUser(String firstname, String lastname, String email, String username, String saltvalue, String encryptedPass){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        int banned = 0;

        String insertFields = "INSERT INTO user_account(firstname,lastname,email,username,encryptedPass,salt,banned) VALUES ('";
        String insertValues = firstname+"','" +lastname+"','" +email+"','" +username+"','" +encryptedPass+"','"+saltvalue+"','"+banned+"')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void validateLogin(String username, String password, Button loginButton)throws CredentialsExceptions,java.sql.SQLException,java.io.IOException{
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

                            Stage stage = (Stage)loginButton.getScene().getWindow();
                            stage.close();

                            FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("marketplaceInterface.fxml"));
                            Scene scene = new Scene(fxmlLoader.load(), 600, 700);
                            stage.setTitle("FS:BZ Marketplace");
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            throw new UserPasswordInvalidException("The username or password is incorrect.Please try again.");
                        }
                    } else {
                        throw new BannedUserException("The user is banned.");
                    }
                }

            }

    }
}
