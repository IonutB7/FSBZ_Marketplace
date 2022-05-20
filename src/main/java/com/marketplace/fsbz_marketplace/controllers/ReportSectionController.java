package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.services.TicketServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportSectionController implements Initializable {
    @FXML private Button goBackButton;
    @FXML private Button sendTicketButton;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField titleTextField;
    @FXML private TextArea contentTextArea;

    @FXML private Label ticketMessageLabel;

    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(goBackButton,"interfaces/marketplaceInterface.fxml",1280,720);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> data = FXCollections.observableArrayList("Bug", "Exploit", "Botting","Sugestion","Others");
        typeComboBox.setItems(data);
    }

    public void setSendTicketButton(ActionEvent event){
        if(typeComboBox!=null&titleTextField.getText().isEmpty()!=true&&contentTextArea.getText().isEmpty()!=true){
            TicketServices.addTicketToDB(UserHolder.getInstance().getUser().getUsername(),titleTextField.getText(),typeComboBox.getValue(),contentTextArea.getText());
            ticketMessageLabel.setText("The ticket was send.");
        }else{
            ticketMessageLabel.setText("Please complete all the fields!");
        }
    }


}
