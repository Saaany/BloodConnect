module com.termproject.bloodconnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    exports com.termproject.Controllers;
    opens com.termproject.Controllers to javafx.fxml;
    exports com.termproject.Clients;
    opens com.termproject.Clients to javafx.fxml;
    exports com.termproject.Server;
    opens com.termproject.Server to javafx.fxml;
    exports com.termproject;
    opens com.termproject to javafx.fxml;
    exports Utils;
    opens Utils to javafx.fxml;

}