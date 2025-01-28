package com.artemsolovev.crmclient.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student extends User {

    private Teacher teacher;

    public Student(@NonNull String login, @NonNull String password, @NonNull String fio, @NonNull String phone) {
        super(login, password, fio, phone);

    }

    @Override
    public String toString() {
        return this.getFio() +
                ", Номер телефона: " + this.getPhone();
    }
}
