package com.marketplace.fsbz_marketplace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminRegister {
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
