package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.prefs.Preferences;

public class TeacherProfileController {
    public TextField loginTextField;
    public TextField passwordTextField;
    public TextField fioTextField;
    public TextField experienceTextField;
    public TextField phoneTextField;
    private Teacher value;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());

    public void initialize() {
        try {
        TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
        this.value = teacherRepository.get(prefs.getInt("Id", -1));
        loginTextField.setText(value.getLogin());
        passwordTextField.setText("***");
        fioTextField.setText(value.getFio());
        phoneTextField.setText(value.getPhone());
        experienceTextField.setText(Integer.toString(value.getExperience()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateButton(ActionEvent actionEvent) throws IOException {
        if (!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()
                && !fioTextField.getText().isEmpty() && !phoneTextField.getText().isEmpty() && !experienceTextField.getText().isEmpty()) {
            TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            if (passwordTextField.getText().equals("***"))
                this.passwordTextField.setText(prefs.get("Password",""));
            Teacher teacher = new Teacher(loginTextField.getText(), passwordTextField.getText(), fioTextField.getText(), phoneTextField.getText(), Integer.parseInt(experienceTextField.getText()));
            teacher.setId(this.value.getId());
            teacherRepository.put(teacher);
            initialize();
        }
    }
}
