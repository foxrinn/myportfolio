package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Student;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.prefs.Preferences;

public class StudentTeachersController {
    public ListView<Teacher> clientsListView;

    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());
    private Student value;

    public void initialize() {
        try {
            StudentRepository studentRepository = new StudentRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.value = studentRepository.get(prefs.getInt("Id", -1));
            List<Teacher> teachers = new ArrayList<>();
            teachers.add(value.getTeacher());
            this.clientsListView.setItems(FXCollections.observableList(teachers));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addButton(ActionEvent actionEvent) throws IOException {
        Main.openWindowAndWait("Добавить репетитора", "addTeacher.fxml", null);
        TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
        this.clientsListView.setItems(FXCollections.observableList(teacherRepository.getAll()));
    }
}
