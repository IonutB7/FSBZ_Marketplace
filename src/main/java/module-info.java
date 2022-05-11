module com.marketplace.fsbz_marketplace {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.marketplace.fsbz_marketplace to javafx.fxml;
    opens com.marketplace.fsbz_marketplace.controllers to javafx.fxml;
    opens com.marketplace.fsbz_marketplace.model to javafx.base;

    exports com.marketplace.fsbz_marketplace;
    exports com.marketplace.fsbz_marketplace.controllers;


}