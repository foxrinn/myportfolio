package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Lesson;
import com.artemsolovev.crmclient.model.Student;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.retrofit.LessonRepository;
import com.artemsolovev.crmclient.retrofit.StudentRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.prefs.Preferences;

public class StudentLessonsController {
    public ListView<Lesson> lessonsListView;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());
    private Student value;

    public void initialize() {
        try {
            StudentRepository studentRepository = new StudentRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.value = studentRepository.get(prefs.getInt("Id", -1));
            LessonRepository lessonRepository = new LessonRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            this.lessonsListView.setItems(FXCollections.observableList(lessonRepository.getByStudent(this.value.getId())));
            lessonsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getClickCount() == 2) {
                        Lesson selectedItem = lessonsListView.getSelectionModel().getSelectedItem();
                        try {
                            Main.openWindowAndWait("Занятие", "lesson.fxml", selectedItem);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLessonButton(ActionEvent actionEvent) throws IOException {
        Main.openWindowAndWait("Добавить занятие", "addLessonStudent", null);
        initialize();
    }

    public void deleteButton(ActionEvent actionEvent) throws IOException {
        if (lessonsListView.getSelectionModel().getSelectedItem() != null) {
            LessonRepository lessonRepository = new LessonRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            lessonRepository.delete(lessonsListView.getSelectionModel().getSelectedItem().getId());
            initialize();
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Выберите тренировку", Alert.AlertType.ERROR);
        }
    }
}
