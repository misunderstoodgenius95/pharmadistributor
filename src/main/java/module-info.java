module pharma {

    requires javafx.fxml;
    requires org.json;
    requires  org.testfx;

    requires jdk.jshell;
    requires org.hamcrest;
    requires org.postgresql.jdbc;
    requires jdk.unsupported;
    requires embedded.postgres;
    requires rgxgen;
    requires java.rmi;
    opens pharma.real to javafx.fxml;
    requires org.kordamp.ikonli.fontawesome5;

    requires commons.math3;
    requires json.path;
    requires org.apache.commons.text;
    requires net.sf.jsqlparser;
    requires net.datafaker;
    requires java.management;
    requires kotlin.stdlib;
    requires auth0;
    requires net.postgis.jdbc.geometry;
    requires javafx.web;
    requires okhttp3;
    requires net.postgis.jdbc;
    requires org.yaml.snakeyaml;
    requires com.auth0.jwt;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.java_websocket;
    requires configcat.java.client;
    requires com.dlsc.gemsfx;
    requires org.controlsfx.controls;
    requires org.jetbrains.annotations;
    requires java.net.http;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;

    exports pharma.real to javafx.graphics, javafx.fxml;
    opens pharma to javafx.fxml;
    exports pharma to javafx.fxml,javafx.graphics;
opens pharma.Controller to javafx.fxml;
opens pharma.Controller.subpanel to javafx.fxml;
exports  pharma.config;
    exports pharma.Controller to javafx.controls, javafx.fxml, javafx.graphics;
exports pharma.Storage;
    opens pharma.config to javafx.fxml;
    exports pharma.dao;
    exports pharma.Model;
    exports pharma.config.View;
    opens pharma.config.View to javafx.fxml;
    exports pharma.DialogController;
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
    opens pharma.javafxlib.Controls.Notification to javafx.fxml;


    opens pharma.Model to com.fasterxml.jackson.databind, javafx.base, javafx.fxml;
    exports pharma.Controller.subpanel to javafx.controls, javafx.fxml, javafx.graphics;
    exports pharma.DialogController.Report;
    opens pharma.Service to javafx.base;
    opens pharma.Utility to javafx.base;
    exports pharma.Utility;

}