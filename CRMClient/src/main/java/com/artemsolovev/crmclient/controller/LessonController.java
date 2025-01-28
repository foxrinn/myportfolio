package com.artemsolovev.crmclient;

import com.artemsolovev.crmclient.controller.ControllerData;
import com.artemsolovev.crmclient.model.Lesson;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LessonController implements ControllerData<Lesson> {
    public TextField teacherTextField;
    public TextField subjectTextField;
    public TextField themeTextField;
    public TextArea homeWorkTextField;
    public CheckBox paymentCheckbox;
    public TextField timeTextField;
    public TextField dateTextField;

    @Override
    public void initData(Lesson value) {
        teacherTextField.setText(value.getTeacher().getFio() + ", Номер телефона: " +value.getTeacher().getPhone());
        subjectTextField.setText(value.getSubject().getName());
        themeTextField.setText(value.getTheme());
        homeWorkTextField.setText(value.getHomework());
        if (value.isPayment()) {
            paymentCheckbox.fire();
        }
        timeTextField.setText(String.valueOf(value.getTimeStart()));
        dateTextField.setText(String.valueOf(value.getDate()));
    }
}
