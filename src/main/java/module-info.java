module pharma {
    requires com.auth0.jwt;
    requires java.net.http;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.json;
    opens pharma to javafx.fxml;
exports pharma.Controller to javafx.controls, javafx.fxml;
    exports pharma to javafx.fxml,javafx.graphics;
opens pharma.Controller to javafx.fxml;
opens pharma.Controller.subpanel to javafx.fxml;
exports pharma.Controller.subpanel to javafx.fxml;
}