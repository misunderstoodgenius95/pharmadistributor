module pharma {
    requires com.auth0.jwt;
    requires java.net.http;
    requires javafx.fxml;
    requires org.json;
    requires  org.testfx;
    requires auth0;
    requires jdk.jshell;
    requires org.hamcrest;
    requires org.postgresql.jdbc;
    requires jdk.unsupported;

    requires rgxgen;
    requires java.rmi;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires  org.controlsfx.controls;
    requires java.desktop;
    requires java.management;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
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