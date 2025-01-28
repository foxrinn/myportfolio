package com.artemsolovev.crmclient.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Teacher extends User {

    private int experience;

    public Teacher(@NonNull String login, @NonNull String password, @NonNull String fio, @NonNull String phone, int experience) {
        super(login, password, fio, phone);
        this.experience = experience;
    }

    @Override
    public String toString() {
        return this.getFio() + ", Опыт: " + experience + " лет";
    }
}
