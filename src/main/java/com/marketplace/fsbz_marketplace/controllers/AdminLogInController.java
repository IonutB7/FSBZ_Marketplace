package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminLogInController {
    @FXML
    private Button registerAdminButton;
    @FXML
    private Button loginAdminButton;
    @FXML
    private Button cancelAdminButton;

    public void setAdminCancelButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(cancelAdminButton,"chooseAccountType.fxml",600,700);
    }
    public void setAdminRegisterButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(registerAdminButton,"adminRegister.fxml",600,700);
    }

}
