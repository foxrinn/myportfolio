package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.Main;
import com.artemsolovev.crmclient.model.Subject;
import com.artemsolovev.crmclient.retrofit.SubjectRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AddSubjectController {
    public TextField subjectTextField;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());

    public void addSubject(ActionEvent actionEvent) throws IOException {
        if (!subjectTextField.getText().isEmpty()) {
            SubjectRepository subjectRepository = new SubjectRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            subjectRepository.post(new Subject(subjectTextField.getText()),prefs.getInt("Id", -1) );
            Main.closeWindow(actionEvent);
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Введите название.", Alert.AlertType.ERROR);
        }
    }
}
