package com.marketplace.fsbz_marketplace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import java.net.URL;

public class AdminLogIn {
    @FXML
    private Button registerAdminButton;
    @FXML
    private Button loginAdminButton;
    @FXML
    private Button cancelAdminButton;

    public void setAdminCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelAdminButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("chooseAccountType.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Choose Account Type");
        stage.setScene(scene);
        stage.show();
    }
    public void setAdminRegisterButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) registerAdminButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("adminRegister.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 700);
        stage.setTitle("Create new account");
        stage.setScene(scene);
        stage.show();
    }

}
