module com.marketplace.fsbz_marketplace {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    //requires mysql.connector.java;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.marketplace.fsbz_marketplace to javafx.fxml;
    exports com.marketplace.fsbz_marketplace;
}