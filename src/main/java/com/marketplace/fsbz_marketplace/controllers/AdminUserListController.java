package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminUserListController {
    @FXML
    private TextField userListSearchTextField;

    @FXML
    private Label userListSearchLabel;
    @FXML
    private Label userListErrorMessageLabel;

    @FXML
    private TableView<User> userListInventoryTableView;
    @FXML
    private Button goBackButton;
    @FXML
    private Button sendWarningButton;
    @FXML
    private Button banUserButton;

    @FXML private TableColumn<Item,Integer> userListAccountIdColumn;
    @FXML private TableColumn<Item,Integer> userListInventoryIdColumn;
    @FXML private TableColumn<Item,String> userListUsernameColumn;
    @FXML private TableColumn<Item,String> userListEmailColumn;
    @FXML private TableColumn<Item,Float> userListBalanceColumn;
    @FXML private TableColumn<Item,Boolean> userListWarnedColumn;

}
