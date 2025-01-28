package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Student;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.prefs.Preferences;

public class TeacherClientsController {
    public ListView<Student> clientsListView;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());
    private Teacher value;

    public void initialize() {
        try {
        TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
        this.value = teacherRepository.get(prefs.getInt("Id", -1));
        StudentRepository studentRepository = new StudentRepository(prefs.get("Login", ""), prefs.get("Password", ""));
        this.clientsListView.setItems(FXCollections.observableList(studentRepository.getByTeacher(this.value.getId())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
