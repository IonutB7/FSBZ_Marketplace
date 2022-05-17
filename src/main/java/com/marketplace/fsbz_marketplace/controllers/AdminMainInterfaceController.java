package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdminMainInterfaceController {
    @FXML
    private Button logOutButton;
    @FXML
    private Button storeInventoryButton;
    @FXML
    private Button userListButton;
    @FXML
    private Button banListButton;
    @FXML
    private Button feedbackSectionButton;

    public void setMyInventoryButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(storeInventoryButton,"interfaces/adminStoreInventory.fxml",1280,720);
    }


    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(logOutButton,"interfaces/adminLogIn.fxml",1280,720);
    }



}
