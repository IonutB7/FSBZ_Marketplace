package com.marketplace.fsbz_marketplace.utilities;

import com.marketplace.fsbz_marketplace.FSBZ_Marketplace;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlUtilities {

    public static void sceneTransiton(Button button,String fxmlFileName,String cssPage, int sceneWidth,int sceneHeight)throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(FSBZ_Marketplace.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(), sceneWidth, sceneHeight);
        scene.getStylesheets().add(App.class.getResource(cssPage).toExternalForm());
        stage.setTitle("FZ:BZ Marketplace");
        stage.setScene(scene);
        stage.show();
    }

}
