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
    private String warningContent;
    private boolean isWarned=false;
    private String banningContent;
    private boolean isBanned=false;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button cancelButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if(fromAdminRegister)
                messageTextArea.setText("Your admin code is:"+adminCode);
            if(isWarned)
                messageTextArea.setText(warningContent);
            if(isBanned)
                messageTextArea.setText(banningContent);
            });
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
        this.isWarned=true;
    }

    public void setBanningContent(String banningContent) {
        this.banningContent = banningContent;
        this.isBanned=true;
    }

    public void setAdminCode(String adminCode){
        this.adminCode=adminCode;
        fromAdminRegister=true;
    }
}
