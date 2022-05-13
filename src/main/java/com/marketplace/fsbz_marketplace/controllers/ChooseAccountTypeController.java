package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;
import java.util.ResourceBundle;

import java.net.URL;

public class ChooseAccountTypeController implements Initializable {
    @FXML
    private Button userButton;
    @FXML
    private Button adminButton;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File logoFile = new File("images/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }

    public void userButtonOnAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("userLogIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("User log-in");
            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) userButton.getScene().getWindow();
            stage1.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void adminButtonOnAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("adminLogIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Admin log-in");
            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) adminButton.getScene().getWindow();
            stage1.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void exitButtonOnAction() {
            Platform.exit();
    }



}