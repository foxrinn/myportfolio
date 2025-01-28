package com.artemsolovev.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "admins")
public class Admin extends User {

    @NonNull
    @Email(message = "Unacceptable email format")
    private String email;

    @NonNull
    @Range(min = 1, max = 1000000, message = "Time expectancy must be greater than 0 and less than 1000000")
    private double salary;

    public Admin(long id, @NonNull String login, @NonNull String password, @NonNull String surname, @NonNull String name,
                 @NonNull String patronymic, @NonNull LocalDateTime regDate, @NonNull String email, @NonNull double salary) {
        super(id, login, password, surname, name, patronymic, regDate);
        this.email = email;
        this.salary = salary;
    }


}
