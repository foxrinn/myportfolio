package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Student;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;

public class StudentMainController {
    public StackPane area;
    private Student value;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());

    public void initialize() {
        try {
            StudentRepository studentRepository = new StudentRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.value = studentRepository.get(prefs.getInt("Id", -1));
            System.out.println(this.value);
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("studentLessons.fxml")));
            area.getChildren().removeAll();
            area.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void profile(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("studentProfile.fxml")));
        area.getChildren().removeAll();
        area.getChildren().setAll(fxml);
    }

    public void teachers(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("studentTeachers.fxml")));
        area.getChildren().removeAll();
        area.getChildren().setAll(fxml);
    }

    public void lessons(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("studentLessons.fxml")));
        area.getChildren().removeAll();
        area.getChildren().setAll(fxml);
    }

    public void logOutButton(ActionEvent actionEvent) throws IOException {
        Main.closeWindow(actionEvent);
        Preferences prefs = Preferences.userRoot().node(Main.class.getName());
        prefs.remove("Id");
        prefs.remove("Login");
        prefs.remove("Password");
        Main.openWindow("Авторизация", "authorization.fxml", null);
    }
}
