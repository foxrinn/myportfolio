package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.controller.ControllerData;
import com.artemsolovev.crmclient.model.User;
import com.artemsolovev.crmclient.retrofit.UserRepository;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Preferences prefs = Preferences.userRoot().node(Main.class.getName());
        String str = "authorization.fxml";
        stage.setTitle("Авторизация");
        if (prefs.getLong("Id", -1) != -1) {
            stage.setTitle("Главная страница");
            UserRepository userRepository = new UserRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            User user = userRepository.get();
            stage.setTitle("Главная страница");
            if (user.getRole().equals("Student"))
                str = "studentMain.fxml";
            if (user.getRole().equals("Teacher"))
                str = "teacherMain.fxml";
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(str));
        Scene scene = new Scene(fxmlLoader.load(), 1120, 740);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static <T> Stage getStage(String name, T data) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(name));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );

        if (data != null) {
            ControllerData<T> controller = loader.getController();
            controller.initData(data);
        }
        return stage;
    }

    public static void showAlertWithoutHeaderText(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static <T> Stage openWindow(String title, String name, T data) throws IOException {
        Stage stage = getStage(name, data);
        stage.setTitle(title);
        stage.show();
        return stage;
    }

    public static <T> Stage openWindowAndWait(String title, String name, T data) throws IOException {
        Stage stage = getStage(name, data);
        stage.setTitle(title);
        stage.showAndWait();
        return stage;
    }

    public static void closeWindow(Event event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}