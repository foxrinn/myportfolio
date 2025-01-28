package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @NonNull
    @NotBlank(message = "Enter login")
    private String login;

    @Column(nullable = false)
    @NonNull
    @NotBlank(message = "Enter password")
    private String password;

    @Column(nullable = false)
    @NonNull
    @NotBlank(message = "Enter last name")
    private String surname;

    @Column(nullable = false)
    @NonNull
    @NotBlank(message = "Enter first name")
    private String name;

    @Column(nullable = false)
    @NonNull
    @NotBlank(message = "Enter patronymic")
    private String patronymic;

    @Column(nullable = false)
    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime regDate = LocalDateTime.now();

    public String getRole() {
        return this.getClass().getSimpleName();
    }
}
