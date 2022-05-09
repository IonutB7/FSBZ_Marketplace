package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminRegisterController {
    @FXML
    private Button registrationAdminButton;
    @FXML
    private Button cancelAdminButton;

    public void setCancelAdminButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelAdminButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("adminLogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Admin log-in");
        stage.setScene(scene);
        stage.show();
    }
}
