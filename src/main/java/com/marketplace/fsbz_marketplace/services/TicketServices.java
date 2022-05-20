package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class TicketServices {

    public static ObservableList<Ticket> getTicketList(){
        return FXCollections.observableArrayList(TicketListHolder.getInstance().getTicketList());
    }

    public static void initializeTicketList(ArrayList<Ticket> retrivedTicketList) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveTickets = "SELECT * FROM ticket_db;";

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveTickets);

            while (queryResult.next()) {

                Ticket tempTicket = new Ticket();

                tempTicket.setTicketId(queryResult.getInt("ticket_id"));
                tempTicket.setSendByUser(queryResult.getString("user"));
                tempTicket.setTitle(queryResult.getString("title"));
                tempTicket.setType(queryResult.getString("type"));
                tempTicket.setContent(queryResult.getString("content"));
                tempTicket.setSendDate(queryResult.getTimestamp("date"));
                tempTicket.setStatus(queryResult.getString("status"));

                retrivedTicketList.add(tempTicket);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
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

    public static void setTicketTableCollumns(TableColumn<Ticket,Integer> ticketIdColumn,
                                              TableColumn<Ticket,String> ticketSendByColumn,
                                              TableColumn<Ticket,String> ticketTitleColumn,
                                              TableColumn<Ticket,String> ticketTypeColumn,
                                              TableColumn<Ticket,Timestamp> ticketSentDateColumn,
                                              TableColumn<Ticket,String> ticketStatusColumn){
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        ticketSendByColumn.setCellValueFactory(new PropertyValueFactory<>("sendByUser"));
        ticketTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ticketTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        ticketSentDateColumn.setCellValueFactory(new PropertyValueFactory<>("sendDate"));
        ticketStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public static boolean verifySearchColumns(Ticket ticket,String lowerCaseFilter){

        if(String.valueOf(ticket.getTicketId()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (ticket.getSendByUser().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (ticket.getTitle().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (ticket.getType().toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (String.valueOf(ticket.getSendDate()).toLowerCase().contains(lowerCaseFilter))
            return true;
        else if (ticket.getStatus().toLowerCase().contains(lowerCaseFilter))
            return true;
        else
            return false;
    }
}
