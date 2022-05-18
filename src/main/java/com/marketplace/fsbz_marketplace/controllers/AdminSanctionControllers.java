package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.UserListHolder;
import com.marketplace.fsbz_marketplace.services.UserListServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSanctionControllers implements Initializable {
    @FXML
    private Label sanctionTextMessage;
    @FXML
    private Label sanctionErrorMessage;
    @FXML
    private Button cancelButton;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea sanctionContentTextArea;


    private boolean adminIsBanning=false;
    private boolean adminIsWarning=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {

            if(adminIsWarning)
                sanctionTextMessage.setText("Write reason for warning the user:");
            if(adminIsBanning)
                sanctionTextMessage.setText("Write reason for banning the user:");
        });
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setSendButtonOnAction(ActionEvent event) throws IOException {
            if(sanctionContentTextArea.getText().isEmpty()==false){
                if(adminIsWarning){
                    UserListServices.sendUserWarningDB(UserListHolder.getInstance().getLastUserId(),sanctionContentTextArea.getText());
                    UserListServices.setWarningForUser(UserListHolder.getInstance().getLastUserId());
                    Stage stage = (Stage) sendButton.getScene().getWindow();
                    stage.close();
                }


            }else{
                sanctionErrorMessage.setText("Please enter a reason for warning.");;
            }
    }

    public void setAdminIsBanning(boolean adminIsBanning) {
        this.adminIsBanning = adminIsBanning;
    }

    public void setAdminIsWarning(boolean adminIsWarning) {
        this.adminIsWarning = adminIsWarning;
    }
}
