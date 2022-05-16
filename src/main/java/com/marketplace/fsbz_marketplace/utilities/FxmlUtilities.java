package com.marketplace.fsbz_marketplace.utilities;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlUtilities {

    public static void sceneTransiton(Button button, String fxmlFileName, int sceneWidth, int sceneHeight)throws IOException {
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

    public static void  setTriColor(AnchorPane anchor, String id){

        Scene scene = anchor.getScene();
        Polygon tri = (Polygon) scene.lookup(id);
        tri.setStyle("-fx-fill: #737373; -fx-stroke: #737373");
    }

}
