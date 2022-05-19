package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserListHolder;
import com.marketplace.fsbz_marketplace.services.UserListServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminBanListController implements Initializable {

    @FXML
    private TextField banListSearchTextField;

    @FXML
    private Label banListSearchLabel;
    @FXML
    private Label banListErrorMessageLabel;


    @FXML
    private Button goBackButton;
    @FXML
    private Button unbanUserButton;


    @FXML private TableView<User> banListInventoryTableView;
    @FXML private TableColumn<Item,Integer> banListAccountIdColumn;
    @FXML private TableColumn<Item,Integer> banListInventoryIdColumn;
    @FXML private TableColumn<Item,String> banListUsernameColumn;
    @FXML private TableColumn<Item,String> banListEmailColumn;
    @FXML private TableColumn<Item,Float> banListBalanceColumn;
    @FXML private TableColumn<Item,Boolean> banListWarnedColumn;


    public void UpdateTable(){
        UserListServices.setUserTableCollumns(banListAccountIdColumn,
                banListInventoryIdColumn,
                banListUsernameColumn,
                banListEmailColumn,
                banListBalanceColumn,
                banListWarnedColumn);

        banListInventoryTableView.setItems(UserListServices.getUserList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {


        UpdateTable();


        FilteredList<User> filteredData = new FilteredList<>(UserListServices.getBannedUserList(), b -> true);

        banListSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(user -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return UserListServices.verifySearchColumns(user,lowerCaseFilter);
        }));


        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(banListInventoryTableView.comparatorProperty());
        banListInventoryTableView.setItems(sortedData);

    }


    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(goBackButton,"interfaces/adminMainInterface.fxml",1280,720);
    }



    public void setUnbanUserButtonOnAction(ActionEvent event) throws IOException {
        if (banListInventoryTableView.getSelectionModel().getSelectedItem() != null) {
            UserListHolder.getInstance().setLastUserId(banListInventoryTableView.getSelectionModel().getSelectedItem().getAcountId());
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("interfaces/adminSanctions.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            AdminSanctionControllers myASC = fxmlLoader.getController();
            myASC.setAdminIsBanning(true);
            stage.setTitle("FZ:BZ Marketplace");
            stage.setScene(scene);
            stage.show();
        } else {
            banListErrorMessageLabel.setText("No user is selected.");
        }
    }
}
