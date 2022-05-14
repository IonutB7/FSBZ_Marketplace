package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
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
        File logoFile = new File("src/main/resources/com/marketplace/fsbz_marketplace/images/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }

    public void userButtonOnAction() {
        try {

            FxmlUtilities.sceneTransiton(userButton,"interfaces/userLogIn.fxml",600,700);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }
    }
    public void adminButtonOnAction() {
        try {
            FxmlUtilities.sceneTransiton(adminButton,"interfaces/adminLogIn.fxml",600,700);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }
    }

    public void exitButtonOnAction() {
            Platform.exit();
    }



}