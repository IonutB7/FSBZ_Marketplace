package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MarketplaceInterfaceController {

    @FXML
    private Button myInventoryButton;
    @FXML private Button logOutButton;



    public void setMyInventoryButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) myInventoryButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("userInventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 700);
        stage.setTitle("My inventory");
        stage.setScene(scene);
        stage.show();
    }

    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("userLogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("User Log-in");
        stage.setScene(scene);
        stage.show();
    }

    private User receiveUserData() {
        return UserHolder.getInstance().getUser();
    }
}
