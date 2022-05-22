package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PopUpsController implements Initializable {

    private String adminCode;
    private boolean fromAdminRegister=false;
    private String sanctionContent;
    private boolean isSanctioned=false;
    private String ticketContent;
    private boolean viewTicket=false;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button cancelButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if(fromAdminRegister)
                messageTextArea.setText("Your admin code is:"+adminCode);
            if(isSanctioned)
                messageTextArea.setText(sanctionContent);
            if(viewTicket)
                messageTextArea.setText(ticketContent);
            });
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setSanctionContent(String sanctionContent) {
        this.sanctionContent = sanctionContent;
        this.isSanctioned=true;
    }

    public void setAdminCode(String adminCode){
        this.adminCode=adminCode;
        fromAdminRegister=true;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
        viewTicket=true;
    }
}
