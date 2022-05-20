package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

public class TicketServices {
    public static void addTicketToDB(String username,String title,String type, String content){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        java.sql.Timestamp date=new java.sql.Timestamp(new Date().getTime());


        String insertFields = "INSERT INTO ticket_db(user,title,type,content,date,status) VALUES ('";
        String insertValues = username+"','"+title+"','"+type+"','" +content+"','" +date+"','" +"Pending"+"')";
        String insertTicketToDB = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertTicketToDB);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
