module com.nasss.taller.motonasss {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.nasss.taller.motonasss.app to javafx.fxml;
    opens com.nasss.taller.motonasss.controllers to javafx.fxml;
    opens com.nasss.taller.motonasss.model to javafx.fxml;
    opens com.nasss.taller.motonasss.services to javafx.fxml;
    opens com.nasss.taller.motonasss.viewController to javafx.fxml;

    exports com.nasss.taller.motonasss.app;
    exports com.nasss.taller.motonasss.controllers;
    exports com.nasss.taller.motonasss.model;
    exports com.nasss.taller.motonasss.services;
    exports com.nasss.taller.motonasss.viewController;
}
