package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.utilities.PassBasedEnc;

import java.sql.Connection;
import java.sql.Statement;

public class AdminService {


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
