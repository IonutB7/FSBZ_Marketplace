package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.*;
import com.marketplace.fsbz_marketplace.model.Admin;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminService {


    public static void initializeAdmin(Admin currentAdmin, String username) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveAdmin = "SELECT * FROM admin_db WHERE username =" + "'" + username + "';";

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveAdmin);

            while (queryResult.next()) {

                int retrivedAdminId=queryResult.getInt("account_id");
                String retrivedEmail=queryResult.getString("email");
                String retrivedUsername=queryResult.getString("username");


                currentAdmin.setAdminId(retrivedAdminId);
                currentAdmin.setEmail(retrivedEmail);
                currentAdmin.setUsername(retrivedUsername);
            }

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public static boolean validateLogin(String adminUsername, String adminPassword,String adminCode)throws CredentialsExceptions,java.sql.SQLException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveEncryptedPassStatement ="SELECT encryptedPass,salt,activated,adminCode FROM admin_db WHERE username = '" + adminUsername +"'";


        Statement statement = connectionDB.createStatement();
        ResultSet queryResult = statement.executeQuery(retriveEncryptedPassStatement);

        if (!queryResult.isBeforeFirst() ) {
            throw new InexistentUserException("The admin does not exist!");
        }else {
            while (queryResult.next()) {
                String proviedPassword = adminPassword;
                String retrivedEncryptedPass = queryResult.getString("encryptedPass");
                String retrivedSalt = queryResult.getString("salt");
                String retrivedAdminCode =queryResult.getString("adminCode");
                int isActivated = queryResult.getInt("activated");
                if (isActivated == 1) {
                    if (PassBasedEnc.verifyUserPassword(proviedPassword, retrivedEncryptedPass, retrivedSalt) == true
                            &&retrivedAdminCode.equals(adminCode)==true) {
                        return true;
                    } else {
                        throw new UserPasswordInvalidException("The username,password or the admin code is incorrect.");
                    }
                } else {
                    throw new AccountNotActivatedException("The account is not yet activated by the admin.");
                }
            }

        }
        return false;
    }

    public static void registerAdmin(String firstname, String lastname, String email, String username, String saltvalue, String encryptedPass,String adminCode){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();


        int activated = 1;


        String insertFields = "INSERT INTO admin_db(firstname,lastname,email,username,encryptedPass,salt,adminCode,activated) VALUES ('";
        String insertValues = firstname+"','" +lastname+"','" +email+"','" +username+"','" +encryptedPass+"','"+saltvalue+"','"+adminCode+"','"+activated+"')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
