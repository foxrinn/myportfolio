package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.Main;
import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.model.WorkDaySchedule;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import com.artemsolovev.crmclient.retrofit.WorkDayScheduleRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class AddTimeController {
    public DatePicker datepicker;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;

    public void initialize() {
        ArrayList<LocalTime> localTimesStarts = new ArrayList<>();
        ArrayList<LocalTime> localTimesEnds = new ArrayList<>();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(21, 0);
        for (LocalTime i = start; !i.equals(end); i = i.plusMinutes(30)) {
            localTimesStarts.add(i);
            localTimesEnds.add(i.plusMinutes(90));
        }
        startTimeComboBox.setItems(FXCollections.observableList(localTimesStarts));
        endTimeComboBox.setItems(FXCollections.observableList(localTimesEnds));
    }
    public void click(Event event) {
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
        datepicker.setDayCellFactory(dayCellFactory);
    }

    public void addTime(ActionEvent actionEvent) throws IOException {
        Preferences prefs = Preferences.userRoot().node(Main.class.getName());
        if (datepicker.getValue() != null
                && startTimeComboBox.getSelectionModel().getSelectedItem() != null
                && endTimeComboBox.getSelectionModel().getSelectedItem() != null) {
            WorkDayScheduleRepository workDayScheduleRepository = new WorkDayScheduleRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            Teacher value = teacherRepository.get(prefs.getInt("Id", -1));
            workDayScheduleRepository.post(new WorkDaySchedule(datepicker.getValue(), startTimeComboBox.getSelectionModel().getSelectedItem(), endTimeComboBox.getSelectionModel().getSelectedItem()), value.getId());
            Main.closeWindow(actionEvent);
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Заполните все поля", Alert.AlertType.ERROR);
        }
    }
}
