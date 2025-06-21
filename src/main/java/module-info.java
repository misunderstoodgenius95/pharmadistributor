module pharma {

    requires java.net.http;
    requires javafx.fxml;
    requires org.json;
    requires  org.testfx;

    requires jdk.jshell;
    requires org.hamcrest;
    requires org.postgresql.jdbc;
    requires jdk.unsupported;

    requires rgxgen;
    requires java.rmi;

    requires  org.controlsfx.controls;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;

    requires commons.math3;
    requires json.path;
    requires org.apache.commons.text;
    requires jdk.xml.dom;
    requires net.sf.jsqlparser;
    requires annotations;
    requires net.datafaker;
    requires java.management;
    requires kotlin.stdlib;
    requires auth0;
    requires com.fasterxml.jackson.databind;
    requires java.sql;


    opens pharma to javafx.fxml;
    exports pharma to javafx.fxml,javafx.graphics;
opens pharma.Controller to javafx.fxml;

opens pharma.Controller.subpanel to javafx.fxml;
exports  pharma.config;
exports pharma.Controller.subpanel to javafx.fxml;
    exports pharma.Controller to javafx.controls, javafx.fxml, javafx.graphics;
exports pharma.Storage;
    opens pharma.config to javafx.fxml;
    opens pharma.Model to javafx.base;
    exports pharma.dao;
    exports pharma.Model;
    exports pharma.security;
    exports pharma.config.View;
    opens pharma.config.View to javafx.fxml;
    exports pharma.Handler;
    exports pharma.config.spinner;
    opens pharma.config.spinner to javafx.fxml;
    exports pharma.config.auth;
    opens pharma.config.auth to javafx.fxml;
    exports pharma.config.net;
    opens pharma.config.net to javafx.fxml;
    exports pharma.javafxlib;
    opens pharma.javafxlib to javafx.fxml;
    exports pharma.javafxlib.Dialog;
    opens pharma.javafxlib.Dialog to javafx.fxml;
    opens pharma.javafxlib.Controls to javafx.fxml;
    exports pharma.javafxlib.Controls;
    exports pharma.config.database;
    opens pharma.config.database to javafx.fxml;
    exports pharma.javafxlib.CustomTableView;
    opens pharma.javafxlib.CustomTableView to javafx.fxml;
    exports pharma.javafxlib.test;
    opens pharma.javafxlib.test to javafx.fxml;
    exports pharma.javafxlib.Controls.Notification;
    opens pharma.testChat to javafx.fxml, javafx.graphics;
    opens pharma.test2 to javafx.graphics;
    opens pharma.javafxlib.Controls.Notification to javafx.fxml;
}