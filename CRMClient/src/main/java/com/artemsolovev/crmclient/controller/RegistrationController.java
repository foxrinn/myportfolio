package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.Main;
import com.artemsolovev.crmclient.model.Student;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegistrationController {
    public TextField loginTextField;

    public TextField passwordTextField;
    public TextField nameTextField;
    public RadioButton trainerButton;
    public RadioButton apprenticeButton;
    public TextField uniqueTextField;
    public Label apprenticeLabel;
    public Label trainerLabel;
    public TextField phoneTextField;
    public Label forStudent;
    public TextField forStudentTextfield;

    public void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        trainerButton.setToggleGroup(toggleGroup);
        apprenticeButton.setToggleGroup(toggleGroup);
    }

    public void trainerButton(ActionEvent actionEvent) {
        forStudent.setText("         Опыт работы");
    }

    public void apprenticeButton(ActionEvent actionEvent) {
        forStudent.setText("Номер репетитора");
    }

    public void registrationButton(ActionEvent actionEvent) {
        try {
            if (nameTextField.getText().isEmpty() || loginTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || uniqueTextField.getText().isEmpty()) {
                Main.showAlertWithoutHeaderText("Ошибка!", "Пожалуйста, заполните все поля!", Alert.AlertType.ERROR);
            } else {
                if (trainerButton.isSelected() && !loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()
                        && !nameTextField.getText().isEmpty() && !uniqueTextField.getText().isEmpty()) {
                    TeacherRepository teacherRepository = new TeacherRepository();
                    teacherRepository.post(new Teacher(loginTextField.getText(), passwordTextField.getText(), nameTextField.getText(), phoneTextField.getText(), Integer.parseInt(forStudentTextfield.getText())));
                    Main.showAlertWithoutHeaderText("Успешно", "Репетитор зарегистрирован.", Alert.AlertType.INFORMATION);
                    Main.closeWindow(actionEvent);
                    Main.openWindow("Авторизация", "authorization.fxml", null);
                } else if (apprenticeButton.isSelected() && !loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()
                        && !nameTextField.getText().isEmpty() && !uniqueTextField.getText().isEmpty()) {
                    StudentRepository studentRepository = new StudentRepository();
                    studentRepository.post(new Student(loginTextField.getText(), passwordTextField.getText(), nameTextField.getText(), phoneTextField.getText()), 2);
                    Main.showAlertWithoutHeaderText("Успешно", "Ученик зарегистрирован.", Alert.AlertType.INFORMATION);
                    Main.closeWindow(actionEvent);
                    Main.openWindow("Авторизация", "authorization.fxml", null);
                } else {
                    Main.showAlertWithoutHeaderText("Ошибка!", "Ошибка в регистрации пользователя!", Alert.AlertType.ERROR);
                }
            }
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            Main.showAlertWithoutHeaderText("Ошибка!", "Ошибка в регистрации пользователя!", Alert.AlertType.ERROR);
        }
    }

    public void signInLink(MouseEvent mouseEvent) throws IOException {
        Main.closeWindow(mouseEvent);
        Main.openWindow("Авторизация", "authorization.fxml", null);
    }
}
