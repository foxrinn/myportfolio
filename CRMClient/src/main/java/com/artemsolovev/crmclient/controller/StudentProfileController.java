package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Student;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.prefs.Preferences;

public class StudentProfileController {
    public TextField loginTextField;
    public TextField passwordTextField;
    public TextField fioTextField;
    public TextField phoneTextField;
    private Student value;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());

    public void initialize() {
        try {
            StudentRepository studentRepository = new StudentRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.value = studentRepository.get(prefs.getInt("Id", -1));
            loginTextField.setText(value.getLogin());
            passwordTextField.setText("***");
            fioTextField.setText(value.getFio());
            phoneTextField.setText(value.getPhone());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateButton(ActionEvent actionEvent) throws IOException {
        if (!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()
                && !fioTextField.getText().isEmpty() && !phoneTextField.getText().isEmpty()) {
            StudentRepository studentRepository = new StudentRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            if (passwordTextField.getText().equals("***"))
                this.passwordTextField.setText(prefs.get("Password",""));
            Student student = new Student(loginTextField.getText(), passwordTextField.getText(), fioTextField.getText(), phoneTextField.getText());
            student.setTeacher(this.value.getTeacher());
            student.setId(this.value.getId());
            studentRepository.put(student);
            initialize();
        }
    }
}
