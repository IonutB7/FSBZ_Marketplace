package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.UserListHolder;
import com.marketplace.fsbz_marketplace.services.UserListServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class AdminWarningControllers {
    @FXML
    private Label warningErrorMessage;
    @FXML
    private Button cancelButton;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea wariningContentTextArea;



    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(cancelButton,"interfaces/adminUserList.fxml",1280,720);
    }

    public void setSendButtonOnAction(ActionEvent event) throws IOException {
            if(wariningContentTextArea.getText().isEmpty()==false){
                UserListServices.sendUserWarningDB(UserListHolder.getInstance().getLastUserId(),wariningContentTextArea.getText());
                UserListServices.setWarningForUser(UserListHolder.getInstance().getLastUserId());
                FxmlUtilities.sceneTransiton(sendButton,"interfaces/adminUserList.fxml",1280,720);

            }else{
                warningErrorMessage.setText("Please enter a reason for warning.");;
            }
    }

}
