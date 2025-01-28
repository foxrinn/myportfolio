package com.artemsolovev.crmclient.controller;

import com.artemsolovev.crmclient.Main;
import com.artemsolovev.crmclient.model.User;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import com.artemsolovev.crmclient.retrofit.UserRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AuthorizationController {
    public TextField loginTextField;
    public TextField passwordTextField;
    public PasswordField passTextField;

    public void signInButton(ActionEvent actionEvent) {
        try {
            if (!loginTextField.getText().isEmpty() && !passTextField.getText().isEmpty()) {
                UserRepository userRepository = new UserRepository(loginTextField.getText(), passTextField.getText());
                User user = userRepository.get();
                if (user != null) {
                    System.out.println(user);
                    Preferences prefs = Preferences.userRoot().node(Main.class.getName());
                    prefs.putLong("Id", user.getId());
                    prefs.put("Login", user.getLogin());
                    prefs.put("Password", passTextField.getText());
                    Main.closeWindow(actionEvent);
                    if (user.getRole().equals("Student"))
                        Main.openWindow("Ученик", "studentMain.fxml", null);
                    if (user.getRole().equals("Teacher"))
                        Main.openWindow("Репетитор", "teacherMain.fxml", null);
                } else {
                    Main.showAlertWithoutHeaderText("Ошибка", "Incorrect login/password", Alert.AlertType.ERROR);
                }
            } else {
                Main.showAlertWithoutHeaderText("Ошибка", "Enter login/password", Alert.AlertType.ERROR);
            }
        } catch (IOException | IllegalArgumentException e) {
            Main.showAlertWithoutHeaderText("Ошибка", "Incorrect login/password", Alert.AlertType.ERROR);
        }
    }

    public void registrationLink(MouseEvent mouseEvent) throws IOException {
        Main.closeWindow(mouseEvent);
        Main.openWindow("Регистрация","registration.fxml", null);
    }
}
