package com.marketplace.fsbz_marketplace.controllers;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientAmountException;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientItemValueException;
import com.marketplace.fsbz_marketplace.exceptions.UserItemsNotSelectedException;
import com.marketplace.fsbz_marketplace.model.Item;
import com.marketplace.fsbz_marketplace.model.SelectedItemsHolder;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import com.marketplace.fsbz_marketplace.services.InventoryServices;
import com.marketplace.fsbz_marketplace.utilities.FxmlUtilities;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MyInventoryController implements Initializable {

    boolean transitionFromPaymentMethod=false;

    @FXML
    private TableView<Item> userInventoryTableView;
    @FXML private TableColumn<Item,Integer> userItemNumberColumn;
    @FXML private TableColumn<Item,Integer> userInventoryIdColumn;
    @FXML private TableColumn<Item,String> userNameColumn;
    @FXML private TableColumn<Item,String> userDescriptionColumn;
    @FXML private TableColumn<Item,String> userCategoryColumn;
    @FXML private TableColumn<Item,String> userWearColumn;
    @FXML private TableColumn<Item,Float> userPriceColumn;
    @FXML private TableColumn<Item,Boolean> userStatTrackColumn;

    @FXML
    private Label userTotalValueLabel;
    @FXML
    private Label userTotalValueMessage;
    @FXML
    private Label tradeItemsMessageLabel;
    @FXML
    private Button userGoBackButton;
    @FXML
    private Button userWalletButton;
    @FXML
    private Button sellItemsButton;
    @FXML
    private Button finishTradeButton;

    public void setUserSelectedItemsList(){
        ObservableList<Item> selectedItems=userInventoryTableView.getSelectionModel().getSelectedItems();
        SelectedItemsHolder holder = SelectedItemsHolder.getInstance();
        holder.setSelectedItemsUserInventory(selectedItems);
        holder.setTotalValueUserItems(InventoryServices.calculateTotalItemValue(selectedItems));
    }
    public void setGoBackButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(userGoBackButton,"marketplaceInterface.fxml",600,700);
    }

    public void setUserWalletButtonOnAction(ActionEvent event) throws IOException {
        FxmlUtilities.sceneTransiton(userWalletButton,"userWallet.fxml",600,700);
    }


    public void setFinishTradeButtonOnAction(ActionEvent event){
        try {
            if(userInventoryTableView.getSelectionModel().getSelectedItems().size()!=0){
                InventoryServices.executePaymentWithItems();
                FxmlUtilities.sceneTransiton(finishTradeButton,"marketplaceInterface.fxml",600,700);
            }else{
                throw new UserItemsNotSelectedException("No items selected!");
            }
        }catch (InsufficientItemValueException exception1){
            tradeItemsMessageLabel.setText(exception1.getMessage());
        }catch (UserItemsNotSelectedException exception2){
            tradeItemsMessageLabel.setText(exception2.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
            Platform.exit();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        InventoryServices services = new InventoryServices();
        services.resetSelectedUserItem();

        finishTradeButton.setVisible(false);
        userGoBackButton.setText("Go back");

        Platform.runLater(() -> {
            if(transitionFromPaymentMethod) {
                finishTradeButton.setVisible(true);
                sellItemsButton.setVisible(false);
                userGoBackButton.setText("Cancel trade");
            }
        });

        userTotalValueLabel.textProperty().bind(Bindings.convert(SelectedItemsHolder.getInstance().totalValueUserItemsProperty()));

        InventoryServices.setInventoryTableCollumns(userItemNumberColumn,
                                                    userInventoryIdColumn,
                                                    userNameColumn,
                                                    userDescriptionColumn,
                                                    userCategoryColumn,
                                                    userWearColumn,
                                                    userPriceColumn,
                                                    userStatTrackColumn);

        userInventoryTableView.setItems(InventoryServices.getUserItems());

        userInventoryTableView.setOnMouseClicked(event -> {
            userInventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setUserSelectedItemsList();
            }
        });

        FxmlUtilities.setMultipleSelctionModeEnable(userInventoryTableView);
    }

    public void setTransitionFromPaymentMethod(boolean transitionFromPaymentMethod) {
        this.transitionFromPaymentMethod = transitionFromPaymentMethod;
    }
}
