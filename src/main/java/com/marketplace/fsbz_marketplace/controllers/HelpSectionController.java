package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.controlsfx.control.action.Action;

import java.io.IOException;

public class HelpSectionController {

    @FXML
    private Button returnButton;

    public void setCancelButtonOnAction(ActionEvent event) throws IOException{
        FxmlUtilities.sceneTransiton(returnButton,"interfaces/marketplaceInterface.fxml",1280,720);
    }
}
