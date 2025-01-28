package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.Main;
import com.artemsolovev.crmclient.model.*;
import com.artemsolovev.crmclient.retrofit.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.prefs.Preferences;

public class AddLessonController {
    public ComboBox<Student> teachersComboBox;
    public DatePicker datePicker;
    public ComboBox<LocalTime> timeComboBox;
    public ComboBox<Subject> subjectsComboBox;
    public TextField themeTextField;
    public TextArea homeWorkTextField;
    public CheckBox paymentCheckbox;
    private Teacher value;
    private Student student;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());

    public void initialize() {
        try {

            TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            StudentRepository studentRepository = new StudentRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            SubjectRepository subjectRepository = new SubjectRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            List<Student> students = studentRepository.getAll();
            teachersComboBox.setItems(FXCollections.observableList(students));
            this.value = teacherRepository.get(prefs.getInt("Id", -1));
            subjectsComboBox.setItems(FXCollections.observableList(subjectRepository.getByTeacher(this.value.getId())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void datePickerClick(Event event) {
        if (teachersComboBox.getSelectionModel().getSelectedItem() != null) {
            WorkDayScheduleRepository workDayScheduleRepository = new WorkDayScheduleRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
                @Override
                public DateCell call(DatePicker datePicker) {
                    return new DateCell() {
                        public void updateItem(LocalDate item, boolean empty) {
                            try {
                                super.updateItem(item, empty);
                                List<WorkDaySchedule> list = workDayScheduleRepository.getByTeacher(value.getId());

                                setDisable(true);
                                for (WorkDaySchedule workDaySchedule : list) {
                                    if (item.isEqual(workDaySchedule.getDate())) {
                                        setDisable(false);
                                        //setStyle("-fx-background-color: #ffc0cb");
                                    }
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                }
            };
            datePicker.setDayCellFactory(dayCellFactory);
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Выберите тренера.", Alert.AlertType.ERROR);
        }
    }

    public void getSchedule(MouseEvent mouseEvent) {
        try {
            if (datePicker.getValue() != null) {
                if (teachersComboBox.getSelectionModel().getSelectedItem() != null) {
                    LessonRepository lessonRepository = new LessonRepository(prefs.get("Login", ""), prefs.get("Password", ""));
                    List<LocalTime> localTimeList = lessonRepository.get(value.getId(), datePicker.getValue());
                    timeComboBox.setItems(FXCollections.observableList(localTimeList));
                } else {
                    Main.showAlertWithoutHeaderText("Ошибка!", "Выберите тренера.", Alert.AlertType.ERROR);
                }
            } else {
                Main.showAlertWithoutHeaderText("Ошибка!", "Выберите дату.", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            Main.showAlertWithoutHeaderText("Ошибка!", "Расписание не найдено.", Alert.AlertType.ERROR);
            timeComboBox.getSelectionModel().clearSelection();
        }
    }

    public void addTraining(ActionEvent actionEvent) {
        try {
        if (teachersComboBox.getSelectionModel().getSelectedItem() != null && datePicker.getValue() != null
                && timeComboBox.getSelectionModel().getSelectedItem() != null && !themeTextField.getText().isEmpty() && !homeWorkTextField.getText().isEmpty()) {
            LessonRepository lessonRepository = new LessonRepository(prefs.get("Login", ""), prefs.get("Password", ""));

            Lesson lesson = new Lesson(paymentCheckbox.isSelected(), true, themeTextField.getText(),
                    homeWorkTextField.getText(), datePicker.getValue(), timeComboBox.getSelectionModel().getSelectedItem());
            lessonRepository.post(lesson, this.value.getId(), teachersComboBox.getSelectionModel().getSelectedItem().getId(), subjectsComboBox.getSelectionModel().getSelectedItem().getId());
            Main.closeWindow(actionEvent);
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Заполните все поля.", Alert.AlertType.ERROR);
        }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
