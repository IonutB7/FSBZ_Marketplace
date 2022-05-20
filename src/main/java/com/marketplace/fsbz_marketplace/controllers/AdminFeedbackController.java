package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.Ticket;
import com.marketplace.fsbz_marketplace.model.TicketListHolder;
import com.marketplace.fsbz_marketplace.model.Transaction;
import com.marketplace.fsbz_marketplace.services.LedgerService;
import com.marketplace.fsbz_marketplace.services.TicketServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        TicketServices.setTicketTableCollumns(ticketIdColumn,
                ticketSendByColumn,
                ticketTitleColumn,
                ticketTypeColumn,
                ticketSentDateColumn,
                ticketStatusColumn);

        ticketTableView.setItems(TicketServices.getTicketList());


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

}
