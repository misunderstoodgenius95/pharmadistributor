module pharma {
    requires com.auth0.jwt;
    requires java.net.http;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.json;
    requires  org.testfx;
    requires auth0;
    requires java.desktop;
    requires jdk.jshell;
    requires org.hamcrest;
    requires org.postgresql.jdbc;
    requires jdk.unsupported;
    requires org.slf4j;
    requires rgxgen;
    opens pharma to javafx.fxml;
    exports pharma to javafx.fxml,javafx.graphics;
opens pharma.Controller to javafx.fxml;
opens pharma.Controller.subpanel to javafx.fxml;
exports  pharma.config;
exports pharma.Controller.subpanel to javafx.fxml;
    exports pharma.Controller to javafx.controls, javafx.fxml, javafx.graphics;
    opens pharma.oldest to javafx.fxml;
    exports pharma.oldest;
    opens pharma.config to javafx.fxml;
    opens pharma.Model to javafx.base;
}