package com.artemsolovev.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin(long id, @NonNull String login, @NonNull String password, @NonNull String fio,
                 @NonNull LocalDateTime regDate) {
        super(id, login, password, fio, regDate);
    }
}
