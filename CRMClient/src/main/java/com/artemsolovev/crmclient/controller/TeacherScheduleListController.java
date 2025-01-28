package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.model.Teacher;
import com.artemsolovev.crmclient.model.WorkDaySchedule;
import com.artemsolovev.crmclient.retrofit.LessonRepository;
import com.artemsolovev.crmclient.retrofit.TeacherRepository;
import com.artemsolovev.crmclient.retrofit.WorkDayScheduleRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalTime;
import java.util.prefs.Preferences;

public class TeacherScheduleListController {
    public TableView<WorkDaySchedule> scheduleTable;
    private Teacher value;
    private Preferences prefs = Preferences.userRoot().node(Main.class.getName());

    public void initialize() {
        try {
        TeacherRepository teacherRepository = new TeacherRepository(prefs.get("Login", ""), prefs.get("Password", ""));
        this.value = teacherRepository.get(prefs.getInt("Id", -1));

        if (scheduleTable.getColumns().size() != 3) {

            TableColumn<WorkDaySchedule, String> dayColumn = new TableColumn<WorkDaySchedule, String>("Дата:");
            dayColumn.setCellValueFactory(new PropertyValueFactory<WorkDaySchedule, String>("date"));
            scheduleTable.getColumns().add(dayColumn);

            TableColumn<WorkDaySchedule, LocalTime> startColumn = new TableColumn<WorkDaySchedule, LocalTime>("Начало работы:");
            startColumn.setPrefWidth(150);
            startColumn.setCellValueFactory(new PropertyValueFactory<WorkDaySchedule, LocalTime>("start"));
            scheduleTable.getColumns().add(startColumn);

            TableColumn<WorkDaySchedule, LocalTime> endColumn = new TableColumn<WorkDaySchedule, LocalTime>("Окончание работы:");
            endColumn.setPrefWidth(196);
            endColumn.setCellValueFactory(new PropertyValueFactory<WorkDaySchedule, LocalTime>("end"));
            scheduleTable.getColumns().add(endColumn);
        }

        WorkDayScheduleRepository workDayScheduleRepository = new WorkDayScheduleRepository(prefs.get("Login", ""), prefs.get("Password", ""));
        scheduleTable.setItems(FXCollections.observableList(workDayScheduleRepository.getByTeacher(this.value.getId())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addButton(ActionEvent actionEvent) throws IOException {
        Main.openWindowAndWait("Добавить рабочий день", "addTime.fxml", null);
        initialize();
    }

    public void deleteButton(ActionEvent actionEvent) throws IOException {
        if (scheduleTable.getSelectionModel().getSelectedItem() != null) {
            WorkDayScheduleRepository workDayScheduleRepository = new WorkDayScheduleRepository(prefs.get("Login", ""), prefs.get("Password", ""));
            workDayScheduleRepository.delete(scheduleTable.getSelectionModel().getSelectedItem().getId());
            initialize();
        } else {
            Main.showAlertWithoutHeaderText("Ошибка!", "Выберите тренировку", Alert.AlertType.ERROR);
        }
    }
}
