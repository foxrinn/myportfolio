package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Lesson;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.LessonRepository;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.prefs.Preferences;

public class TeacherLessonsController {
    public ListView<Lesson> lessonsListView;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());
    private Teacher value;

    public void initialize() {
        try {
            TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.value = teacherRepository.get(prefs.getInt("Id", -1));
            LessonRepository lessonRepository = new LessonRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.lessonsListView.setItems(FXCollections.observableList(lessonRepository.getByTeacher(this.value.getId())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLessonButton(ActionEvent actionEvent) throws IOException {
        Main.openWindowAndWait("Добавить занятие", "addLesson.fxml", null);
        initialize();
    }

    public void deleteLessonButton(ActionEvent actionEvent) throws IOException {
        if (lessonsListView.getSelectionModel().getSelectedItem() != null) {
            LessonRepository lessonRepository = new LessonRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            lessonRepository.delete(lessonsListView.getSelectionModel().getSelectedItem().getId());
            initialize();
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Выберите тренировку", Alert.AlertType.ERROR);
        }
    }
}
