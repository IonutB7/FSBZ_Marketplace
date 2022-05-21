package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.AdminStoreControllerHolder;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

        Stage stage = (Stage) storeInventoryButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("interfaces/adminStoreInventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        AdminStoreControllerHolder.getInstance().setAdminStoreInventoryController(fxmlLoader.getController());
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }

    public void setUserListButtonOnAction(ActionEvent event)throws IOException{
        FxmlUtilities.sceneTransiton(userListButton,"interfaces/adminUserList.fxml",1280,720);
    }

    public void setBanListButtonOnAction(ActionEvent event)throws IOException{
        FxmlUtilities.sceneTransiton(banListButton,"interfaces/adminBanList.fxml",1280,720);
    }


    public void setCancelButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(logOutButton,"interfaces/adminLogIn.fxml",1280,720);
    }

    public void setFeedbackSectionButtonOnAction(ActionEvent event) throws IOException{
        FxmlUtilities.sceneTransiton(feedbackSectionButton,"interfaces/adminFeedback.fxml",1280,720);

    }


}
