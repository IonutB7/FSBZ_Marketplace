package com.marketplace.fsbz_marketplace.services;


import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.*;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;


import javafx.collections.ObservableList;
import javafx.scene.control.Button;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServices {


    public static void updateUserBalance(float newBalanceValue,int accId){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();


        String updateUserBalance = "UPDATE user_account SET balance ="+newBalanceValue+" WHERE account_id ="+accId+";";

        try{

            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(updateUserBalance);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void addUserSelectedItems(ObservableList<Item> selectedItems){
        for(Item item:selectedItems){
            UserHolder.getInstance().getUser().getUserInventory().add(item);
        }
    }
    public static void removeUserSelectedItems(ObservableList<Item> selectedItems){
        for(Item item:selectedItems){
            UserHolder.getInstance().getUser().getUserInventory().remove(item);
        }
    }
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
                float retrivedBalance=queryResult.getFloat("balance");


                currentUser.setAcountId(retrivedAcountId);
                currentUser.setInventoryId(retrivedInventoryId);
                currentUser.setEmail(retrivedEmail);
                currentUser.setUsername(retrivedUsername);
                currentUser.setBalance(retrivedBalance);

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
        float balance = 1000;
        int inventory_id=retriveLastUserId(connectDB);
        inventory_id++;

        String insertFields = "INSERT INTO user_account(inventory_id,firstname,lastname,email,username,encryptedPass,salt,balance,banned,adminAproved) VALUES ('";
        String insertValues = inventory_id+"','"+firstname+"','" +lastname+"','" +email+"','" +username+"','" +encryptedPass+"','"+saltvalue+"','"+balance+"','"+banned+"','"+adminAproved+"')";
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
                            throw new UserPasswordInvalidException("The username or password is incorrect.");
                        }
                    } else {
                        throw new BannedUserException("The user is banned.");
                    }
                }

            }
        return false;
    }


    public static void verifyEmptyFilds(String firstname,String lastname,String email,String  username, String password)throws CredentialsExceptions{
        if(firstname.isEmpty())
            throw new EmptyFieldException("There exits incompleted fields!");
        if(lastname.isEmpty())
            throw new EmptyFieldException("There exits incompleted fields!");
        if(email.isEmpty())
            throw new EmptyFieldException("There exits incompleted fields!");
        if(username.isEmpty())
            throw new EmptyFieldException("There exits incompleted fields!");
        if(password.isEmpty())
            throw new EmptyFieldException("There exits incompleted fields!");
    }

    public static void verifytLenghtCredenial(String firstname,String lastname,String  username)throws CredentialsExceptions{
        if(firstname.length()<3||lastname.length()<3||username.length()<3){
            throw new InsuficientLenghtException("The First Name,Last Name and Username needs to be at least 3 letters long!");
        }
    }

    public static void verifyEmailCorrectness(String email)throws CredentialsExceptions{
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        boolean matchFound = m.matches();
        if (!matchFound) {
            throw new IncorectEmailException("Invalid email!");
        }

    }

    public static void verifyPasswordCorrectness(String password) throws CredentialsExceptions {
    if(!password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}"))
        throw  new IncorrectPasswordExeption("The passowrd does not corespond to the needed format!");
    }

}
