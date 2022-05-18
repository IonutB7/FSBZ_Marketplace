package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.services.UserListServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminUserListController implements Initializable {


    @FXML
    private TextField userListSearchTextField;

    @FXML
    private Label userListSearchLabel;
    @FXML
    private Label userListErrorMessageLabel;


    @FXML
    private Button goBackButton;
    @FXML
    private Button sendWarningButton;
    @FXML
    private Button banUserButton;


    @FXML
    private TableView<User> userListInventoryTableView;
    @FXML private TableColumn<Item,Integer> userListAccountIdColumn;
    @FXML private TableColumn<Item,Integer> userListInventoryIdColumn;
    @FXML private TableColumn<Item,String> userListUsernameColumn;
    @FXML private TableColumn<Item,String> userListEmailColumn;
    @FXML private TableColumn<Item,Float> userListBalanceColumn;
    @FXML private TableColumn<Item,Boolean> userListWarnedColumn;


    public void UpdateTable(){
        UserListServices.setUserTableCollumns(userListAccountIdColumn,
                userListInventoryIdColumn,
                userListUsernameColumn,
                userListEmailColumn,
                userListBalanceColumn,
                userListWarnedColumn);

        userListInventoryTableView.setItems(UserListServices.getUserList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {


        UpdateTable();


        FilteredList<User> filteredData = new FilteredList<>(UserListServices.getUserList(), b -> true);

        userListSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(user -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return UserListServices.verifySearchColumns(user,lowerCaseFilter);
        }));


        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userListInventoryTableView.comparatorProperty());
        userListInventoryTableView.setItems(sortedData);

    }


    public void setGoBackButtonOnAction(ActionEvent event)  throws IOException {
        FxmlUtilities.sceneTransiton(goBackButton,"interfaces/adminMainInterface.fxml",1280,720);
    }


}
