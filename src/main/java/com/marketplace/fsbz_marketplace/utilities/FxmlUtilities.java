package com.marketplace.fsbz_marketplace.utilities;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import com.marketplace.fsbz_marketplace.controllers.PopUpsController;
import com.marketplace.fsbz_marketplace.services.UserServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class FxmlUtilities {

    public static void sceneTransiton(Button button,String fxmlFileName,int sceneWidth,int sceneHeight)throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(), sceneWidth, sceneHeight);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }

    public static void sceneTransiton1(AnchorPane anchor, String fxmlFileName, int sceneWidth, int sceneHeight)throws IOException {
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(), sceneWidth, sceneHeight);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }
    public static void sceneTransiton2(String fxmlFileName, int sceneWidth, int sceneHeight)throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(), sceneWidth, sceneHeight);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }
    public static void sceneTransiton3(Button button)throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public static void setSanctionPopUp(String username) throws IOException,SQLException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource("interfaces/popUps.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 530);
        String sanctionContent= UserServices.getSanctionContent(username);
        PopUpsController myPUC = fxmlLoader.getController();
        myPUC.setSanctionContent(sanctionContent);
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }

    public static void setMultipleSelctionModeEnable(TableView tableView) {
        tableView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            while (node != null && node != tableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            if (node instanceof TableRow) {
                evt.consume();

                TableRow row = (TableRow) node;
                TableView tv = row.getTableView();

                tv.requestFocus();

                if (!row.isEmpty()) {
                    int index = row.getIndex();
                    if (row.isSelected()) {
                        tv.getSelectionModel().clearSelection(index);
                    } else {
                        tv.getSelectionModel().select(index);
                    }
                }
            }
        });
    }

}
