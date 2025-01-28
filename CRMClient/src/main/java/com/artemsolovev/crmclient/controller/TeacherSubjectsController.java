package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Subject;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.LessonRepository;
import com.artemsolovev.crmclient.retrofit.SubjectRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.prefs.Preferences;

public class TeacherSubjectsController {
    public ListView<Subject> subjectsListView;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());
    private Teacher value;

    public void initialize() {
        try {
            TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.value = teacherRepository.get(prefs.getInt("Id", -1));
            SubjectRepository subjectRepository = new SubjectRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.subjectsListView.setItems(FXCollections.observableList(subjectRepository.getByTeacher(this.value.getId())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addButton(ActionEvent actionEvent) throws IOException {
        Main.openWindowAndWait("Добавить дисциплину", "addSubject.fxml", null);
        initialize();
    }

    public void deleteButton(ActionEvent actionEvent) throws IOException {
        if (subjectsListView.getSelectionModel().getSelectedItem() != null) {
            SubjectRepository subjectRepository = new SubjectRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            subjectRepository.delete(subjectsListView.getSelectionModel().getSelectedItem().getId());
            initialize();
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Выберите тренировку", Alert.AlertType.ERROR);
        }
    }
}
