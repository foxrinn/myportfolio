module com.artemsolovev.crmclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires static lombok;
    requires retrofit2;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires okhttp3;
    requires retrofit2.converter.jackson;
    requires java.prefs;


    opens com.artemsolovev.crmclient to javafx.fxml;
    exports com.artemsolovev.crmclient;
    opens com.artemsolovev.crmclient.controller to javafx.fxml;
    exports com.artemsolovev.crmclient.dto to com.fasterxml.jackson.databind;
    exports com.artemsolovev.crmclient.model to com.fasterxml.jackson.databind;
    exports com.artemsolovev.crmclient.controller;
    opens com.artemsolovev.crmclient.model to javafx.base;
}