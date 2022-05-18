package com.marketplace.fsbz_marketplace.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUpsController implements Initializable {

    private String adminCode;
    @FXML
    private TextArea adminCodeTextArea;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            adminCodeTextArea.setText("Your admin code is:"+adminCode);
            });
    }

    public void setAdminCode(String adminCode){
        this.adminCode=adminCode;
    }
}
