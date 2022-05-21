package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.*;
import com.marketplace.fsbz_marketplace.services.AdminService;
import com.marketplace.fsbz_marketplace.services.LedgerService;
import com.marketplace.fsbz_marketplace.services.TicketServices;
import com.marketplace.fsbz_marketplace.services.UserListServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AdminFeedbackController implements Initializable {
    @FXML
    private TableView<Ticket> ticketTableView;
    @FXML private TableColumn<Ticket,Integer> ticketIdColumn;
    @FXML private TableColumn<Ticket,String> ticketSendByColumn;
    @FXML private TableColumn<Ticket,String> ticketTitleColumn;
    @FXML private TableColumn<Ticket,String> ticketTypeColumn;
    @FXML private TableColumn<Ticket,Timestamp> ticketSentDateColumn;
    @FXML private TableColumn<Ticket,String> ticketStatusColumn;


    @FXML
    private TextField searchTextField;
    @FXML
    private Button goBackButton;
    @FXML
    private Button viewContentButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button declineButton;
    @FXML
    private Label ticketMessageLabel;

    public void UpdateTable(){
        TicketServices.setTicketTableCollumns(ticketIdColumn,
                ticketSendByColumn,
                ticketTitleColumn,
                ticketTypeColumn,
                ticketSentDateColumn,
                ticketStatusColumn);

        ticketTableView.setItems(TicketServices.getTicketList());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        UpdateTable();


        FilteredList<Ticket> filteredData = new FilteredList<>(TicketServices.getTicketList(), b -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(ticket -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return TicketServices.verifySearchColumns(ticket,lowerCaseFilter);
        }));


        SortedList<Ticket> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ticketTableView.comparatorProperty());
        ticketTableView.setItems(sortedData);
    }

    public void setGoBackButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(goBackButton,"interfaces/adminMainInterface.fxml",1280,720);
    }
    public void setViewContentButtonOnAction(ActionEvent event) throws IOException{
        if(ticketTableView.getSelectionModel().getSelectedItem()!=null){
            Ticket selectedTicket =ticketTableView.getSelectionModel().getSelectedItem();
            TicketServices.changeTicketStatusDB(selectedTicket.getTicketId(),"Viewed");
            TicketServices.changeTicketStatus(selectedTicket.getTicketId(),"Viewed");
            UpdateTable();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("interfaces/popUps.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            PopUpsController myPOC = fxmlLoader.getController();
            myPOC.setTicketContent(selectedTicket.getContent());
            stage.setTitle("FZ:BZ Marketplace");
            stage.setScene(scene);
            stage.show();

        }else{
            ticketMessageLabel.setText("No ticket is selected.");
        }
    }

    public void setDeclineButtonOnAction(ActionEvent event) {
        if (ticketTableView.getSelectionModel().getSelectedItem() != null) {
            if (ticketTableView.getSelectionModel().getSelectedItem().getType().equals("Activation")) {
                Ticket selectedTicket = ticketTableView.getSelectionModel().getSelectedItem();
                TicketServices.changeTicketStatusDB(selectedTicket.getTicketId(), "Rejected");
                TicketServices.changeTicketStatus(selectedTicket.getTicketId(), "Rejected");
                AdminService.declineAdminRequest(selectedTicket.getSendByUser());
                UpdateTable();
            }else{
                ticketMessageLabel.setText("The ticket is not an admin request!");
            }
        } else {
            ticketMessageLabel.setText("No ticket is selected.");
        }
    }

    public void setAcceptButtonOnAction(ActionEvent event) {
        if (ticketTableView.getSelectionModel().getSelectedItem() != null) {
            if (ticketTableView.getSelectionModel().getSelectedItem().getType().equals("Activation")) {
                Ticket selectedTicket = ticketTableView.getSelectionModel().getSelectedItem();
                TicketServices.changeTicketStatusDB(selectedTicket.getTicketId(), "Accepted");
                TicketServices.changeTicketStatus(selectedTicket.getTicketId(), "Accepted");
                AdminService.activateAdminAccount(selectedTicket.getSendByUser());
                UpdateTable();
            }else{
                ticketMessageLabel.setText("The ticket is not an admin request!");
            }
        } else {
            ticketMessageLabel.setText("No ticket is selected.");
        }
    }

}
